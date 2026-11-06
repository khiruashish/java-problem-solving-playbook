// Mouse-follow 3D tilt for cards. Re-binds on Material's instant navigation.
(function () {
  const reduce = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
  function bind() {
    if (reduce) return;
    document.querySelectorAll(".md-typeset .grid.cards > ul > li, .pb-card").forEach((el) => {
      if (el.dataset.tilt) return;
      el.dataset.tilt = "1";
      el.classList.add("pb-tilt");
      el.addEventListener("mousemove", (e) => {
        const r = el.getBoundingClientRect();
        const x = (e.clientX - r.left) / r.width - 0.5;
        const y = (e.clientY - r.top) / r.height - 0.5;
        el.style.transform =
          `perspective(900px) rotateX(${(-y * 8).toFixed(2)}deg) rotateY(${(x * 10).toFixed(2)}deg) translateY(-6px)`;
      });
      el.addEventListener("mouseleave", () => { el.style.transform = ""; });
    });
  }
  if (window.document$) { window.document$.subscribe(bind); } else { document.addEventListener("DOMContentLoaded", bind); }
})();
