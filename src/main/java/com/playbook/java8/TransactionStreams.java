package com.playbook.java8;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/** The "transactions dataset" round — common in fintech / banking interviews (Medium → Hard). */
public final class TransactionStreams {
    private TransactionStreams() {}

    public static List<Transaction> sample() {
        return List.of(
                new Transaction(1, "ACC-1", "CREDIT", 5_000_000, "Salary", LocalDate.of(2026, 1, 1)),
                new Transaction(2, "ACC-1", "DEBIT", 120_000, "Grocer", LocalDate.of(2026, 1, 3)),
                new Transaction(3, "ACC-1", "DEBIT", 45_000, "Cafe", LocalDate.of(2026, 1, 3)),
                new Transaction(4, "ACC-2", "CREDIT", 3_000_000, "Salary", LocalDate.of(2026, 1, 1)),
                new Transaction(5, "ACC-2", "DEBIT", 1_500_000, "Rent", LocalDate.of(2026, 1, 5)),
                new Transaction(6, "ACC-1", "DEBIT", 250_000, "Grocer", LocalDate.of(2026, 2, 2)),
                new Transaction(7, "ACC-3", "CREDIT", 800_000, "Refund", LocalDate.of(2026, 2, 7)),
                new Transaction(8, "ACC-2", "DEBIT", 60_000, "Cafe", LocalDate.of(2026, 2, 9)),
                new Transaction(9, "ACC-1", "DEBIT", 2_000_000, "Travel", LocalDate.of(2026, 2, 14)),
                new Transaction(10, "ACC-3", "DEBIT", 30_000, "Cafe", LocalDate.of(2026, 2, 15)));
    }

    /** Q1. Net balance per account (credits − debits), sorted by account. */
    public static Map<String, Long> balanceByAccount(List<Transaction> ts) {
        return ts.stream().collect(Collectors.groupingBy(Transaction::account, TreeMap::new,
                Collectors.summingLong(Transaction::signedPaise)));
    }

    /** Q2. Total debit spend per month. */
    public static Map<YearMonth, Long> spendByMonth(List<Transaction> ts) {
        return ts.stream().filter(Transaction::isDebit)
                .collect(Collectors.groupingBy(t -> YearMonth.from(t.date()), TreeMap::new,
                        Collectors.summingLong(Transaction::amountPaise)));
    }

    /** Q3. Largest debit per account (the whole transaction id). */
    public static Map<String, Long> largestDebitIdPerAccount(List<Transaction> ts) {
        return ts.stream().filter(Transaction::isDebit)
                .collect(Collectors.groupingBy(Transaction::account, TreeMap::new,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingLong(Transaction::amountPaise)),
                                o -> o.orElseThrow().id())));
    }

    /** Q4. Top-N merchants by total debit spend (ties broken by name). */
    public static List<String> topMerchants(List<Transaction> ts, int n) {
        return ts.stream().filter(Transaction::isDebit)
                .collect(Collectors.groupingBy(Transaction::merchant, Collectors.summingLong(Transaction::amountPaise)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(n)
                .map(Map.Entry::getKey)
                .toList();
    }

    /** Q5. Partition debits into large (≥ threshold) and small, counting each side. */
    public static Map<Boolean, Long> countLargeDebits(List<Transaction> ts, long thresholdPaise) {
        return ts.stream().filter(Transaction::isDebit)
                .collect(Collectors.partitioningBy(t -> t.amountPaise() >= thresholdPaise, Collectors.counting()));
    }

    /** Q6. Debit statistics (count, min, max, sum, average) in one pass. */
    public static LongSummaryStatistics debitStats(List<Transaction> ts) {
        return ts.stream().filter(Transaction::isDebit).mapToLong(Transaction::amountPaise).summaryStatistics();
    }

    /** Q7. Accounts with more than k debits, sorted. */
    public static List<String> accountsWithMoreThanKDebits(List<Transaction> ts, long k) {
        return ts.stream().filter(Transaction::isDebit)
                .collect(Collectors.groupingBy(Transaction::account, Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() > k)
                .map(Map.Entry::getKey).sorted().toList();
    }

    /** Q8. First transaction of each day (by id), keeping days sorted. */
    public static Map<LocalDate, Long> firstTxnIdPerDay(List<Transaction> ts) {
        return ts.stream().collect(Collectors.toMap(Transaction::date, Transaction::id, Math::min, TreeMap::new));
    }

    /** Q9. Distinct merchants per account, comma-joined and sorted. */
    public static Map<String, String> merchantsPerAccount(List<Transaction> ts) {
        return ts.stream().collect(Collectors.groupingBy(Transaction::account, TreeMap::new,
                Collectors.collectingAndThen(
                        Collectors.mapping(Transaction::merchant, Collectors.toCollection(java.util.TreeSet::new)),
                        set -> String.join(", ", set))));
    }

    /**
     * Q10. Running balance of one account after each transaction, in date-then-id order.
     * This is a PREFIX SUM: streams have no built-in scan, and a stateful lambda breaks under parallel(),
     * so the honest answer is a plain loop over the sorted stream's list.
     */
    public static List<Long> runningBalance(List<Transaction> ts, String account) {
        List<Transaction> sorted = ts.stream().filter(t -> t.account().equals(account))
                .sorted(Comparator.comparing(Transaction::date).thenComparingLong(Transaction::id))
                .toList();
        List<Long> out = new ArrayList<>(sorted.size());
        long balance = 0;
        for (Transaction t : sorted) out.add(balance += t.signedPaise());
        return out;
    }

    /** Q11. Is any account overdrawn at any point in time? (anyMatch over each account's running balance) */
    public static Optional<String> firstOverdrawnAccount(List<Transaction> ts) {
        return ts.stream().map(Transaction::account).distinct().sorted()
                .filter(acc -> runningBalance(ts, acc).stream().anyMatch(b -> b < 0))
                .findFirst();
    }

    /** Format paise as rupees with exactly two decimals, without floating point. */
    public static String rupees(long paise) {
        String sign = paise < 0 ? "-" : "";
        long abs = Math.abs(paise);
        return sign + "₹" + (abs / 100) + "." + String.format("%02d", abs % 100);
    }
}
