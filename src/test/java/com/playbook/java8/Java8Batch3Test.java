package com.playbook.java8;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Java8Batch3Test {

    static final List<String> WORDS = List.of("apple", "avocado", "banana", "blueberry", "cherry", "kiwi", "apricot", "fig");

    @Test void mapAndCollectors() {
        assertEquals(Map.of("a", 2, "b", 1), MapCollectorsAdvanced.wordCountMerge(List.of("a", "b", "a")));
        assertEquals(Map.of('a', List.of("apple", "avocado", "apricot"), 'b', List.of("banana", "blueberry"),
                        'c', List.of("cherry"), 'k', List.of("kiwi"), 'f', List.of("fig")),
                MapCollectorsAdvanced.groupByFirstLetter(WORDS));
        assertEquals(Map.of('a', "avocado", 'b', "blueberry", 'c', "cherry", 'k', "kiwi", 'f', "fig"),
                MapCollectorsAdvanced.longestPerFirstLetter(WORDS));               // avocado and apricot tie at 7 → first wins
        assertEquals(Map.of(1, Set.of("x", "z"), 2, Set.of("y")),
                MapCollectorsAdvanced.invert(Map.of("x", 1, "y", 2, "z", 1)));
        assertEquals(Map.of(3, Map.of('f', 1L), 4, Map.of('k', 1L), 5, Map.of('a', 1L),
                        6, Map.of('b', 1L, 'c', 1L), 7, Map.of('a', 2L), 9, Map.of('b', 1L)),
                MapCollectorsAdvanced.lengthThenLetter(WORDS));
        Map<Character, List<String>> keep = MapCollectorsAdvanced.longWordsPerLetterKeepEmpty(WORDS, 7);
        Map<Character, List<String>> drop = MapCollectorsAdvanced.longWordsPerLetterDropEmpty(WORDS, 7);
        assertEquals(List.of(), keep.get('k'));                                  // key kept, empty list
        assertFalse(drop.containsKey('k'));                                      // key gone
        assertEquals(List.of("avocado", "apricot"), keep.get('a'));
        assertEquals(Set.of('f', 'g', 'i'), MapCollectorsAdvanced.charsPerLength(WORDS).get(3));
        assertEquals(List.of("apricot", "avocado"), MapCollectorsAdvanced.topTwoLongestPerLetter(WORDS).get('a'));
        assertEquals(List.of("c", "a", "b"),
                new ArrayList<>(MapCollectorsAdvanced.sortByValueDesc(Map.of("a", 2, "b", 2, "c", 5)).keySet()));
        assertEquals(Map.of("a", 3, "b", 2, "c", 4),
                MapCollectorsAdvanced.mergeCounts(Map.of("a", 1, "b", 2), Map.of("a", 2, "c", 4)));
        assertEquals(Map.of("t", 9, "u", 3), MapCollectorsAdvanced.maxPerKey(
                List.of(Map.entry("t", 4), Map.entry("u", 3), Map.entry("t", 9), Map.entry("t", 1))));
    }

    @Test void transactions() {
        List<Transaction> ts = TransactionStreams.sample();
        assertEquals(Map.of("ACC-1", 2_585_000L, "ACC-2", 1_440_000L, "ACC-3", 770_000L),
                TransactionStreams.balanceByAccount(ts));
        assertEquals(Map.of(YearMonth.of(2026, 1), 1_665_000L, YearMonth.of(2026, 2), 2_340_000L),
                TransactionStreams.spendByMonth(ts));
        assertEquals(Map.of("ACC-1", 9L, "ACC-2", 5L, "ACC-3", 10L), TransactionStreams.largestDebitIdPerAccount(ts));
        assertEquals(List.of("Travel", "Rent"), TransactionStreams.topMerchants(ts, 2));
        assertEquals(Map.of(true, 2L, false, 5L), TransactionStreams.countLargeDebits(ts, 1_000_000));
        LongSummaryStatistics st = TransactionStreams.debitStats(ts);
        assertEquals(7, st.getCount());
        assertEquals(4_005_000L, st.getSum());
        assertEquals(30_000L, st.getMin());
        assertEquals(2_000_000L, st.getMax());
        assertEquals(List.of("ACC-1"), TransactionStreams.accountsWithMoreThanKDebits(ts, 2));
        Map<LocalDate, Long> first = TransactionStreams.firstTxnIdPerDay(ts);
        assertEquals(8, first.size());
        assertEquals(2L, first.get(LocalDate.of(2026, 1, 3)));
        assertEquals("Cafe, Grocer, Salary, Travel", TransactionStreams.merchantsPerAccount(ts).get("ACC-1"));
        assertEquals(List.of(5_000_000L, 4_880_000L, 4_835_000L, 4_585_000L, 2_585_000L),
                TransactionStreams.runningBalance(ts, "ACC-1"));
        assertEquals(Optional.empty(), TransactionStreams.firstOverdrawnAccount(ts));
        List<Transaction> more = new ArrayList<>(ts);
        more.add(new Transaction(11, "ACC-3", "DEBIT", 900_000, "Rent", LocalDate.of(2026, 2, 20)));
        assertEquals(Optional.of("ACC-3"), TransactionStreams.firstOverdrawnAccount(more));
        assertEquals("₹25850.00", TransactionStreams.rupees(2_585_000));
        assertEquals("-₹0.05", TransactionStreams.rupees(-5));
    }

    @Test void lazyAndIndexed() {
        assertEquals(List.of("a-1", "b-2"), LazyStreamTricks.zip(List.of("a", "b", "c"), List.of("1", "2")));
        assertEquals(List.of(4, -3), LazyStreamTricks.adjacentDifferences(List.of(3, 7, 4)));
        assertEquals(List.of(), LazyStreamTricks.adjacentDifferences(List.of(3)));
        assertEquals(List.of(List.of(1, 2), List.of(2, 3), List.of(3, 4)), LazyStreamTricks.windows(List.of(1, 2, 3, 4), 2));
        assertEquals(List.of(), LazyStreamTricks.windows(List.of(1), 2));
        assertEquals(List.of(1, 3), LazyStreamTricks.indexesOfNegatives(new int[]{5, -1, 0, -7}));
        assertEquals(List.of(0L, 1L, 1L, 2L, 3L, 5L, 8L, 13L), LazyStreamTricks.fibonacci(8));
        assertEquals(List.of(1L, 2L, 4L, 8L, 16L), LazyStreamTricks.powersOfTwoUpTo(20));
        assertEquals(List.of(3, 1), LazyStreamTricks.leadingPositives(List.of(3, 1, -2, 5)));
        assertEquals(List.of(-2, 5), LazyStreamTricks.afterLeadingPositives(List.of(3, 1, -2, 5)));
        assertEquals(List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29), LazyStreamTricks.firstPrimes(10));
        assertEquals(3, LazyStreamTricks.elementsTouchedUntilFirstMatch(List.of(4, 8, 15, 16, 23), 15));
        assertEquals(List.of("filter:b", "map:b", "forEach:b", "filter:a", "map:a", "forEach:a"),
                LazyStreamTricks.executionOrder(List.of("b", "a"), false));
        assertEquals(List.of("filter:b", "filter:a", "map:a", "forEach:a", "map:b", "forEach:b"),
                LazyStreamTricks.executionOrder(List.of("b", "a"), true));
        assertEquals(List.of(List.of(1, 2), List.of(3, 4), List.of(5)), LazyStreamTricks.chunks(List.of(1, 2, 3, 4, 5), 2));
        assertEquals(List.of(3, 2, 1), LazyStreamTricks.reversed(List.of(1, 2, 3)));
        assertThrows(IllegalArgumentException.class, () -> LazyStreamTricks.chunks(List.of(1), 0));
    }
}
