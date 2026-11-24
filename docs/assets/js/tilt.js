// Neon-glass interactions: mouse-follow 3D tilt + spotlight on cards, parallax on the hero cube.
// Re-binds on Material's instant navigation; does nothing under prefers-reduced-motion or touch.
(function () {
  const reduce = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
  const finePointer = window.matchMedia("(pointer: fine)").matches;

  function bindCards() {
    document.querySelectorAll(".md-typeset .grid.cards > ul > li, .pb-card").forEach((el) => {
      if (el.dataset.tilt) return;
      el.dataset.tilt = "1";
      el.classList.add("pb-tilt");
      el.addEventListener("mousemove", (e) => {
        const r = el.getBoundingClientRect();
        const x = (e.clientX - r.left) / r.width;
        const y = (e.clientY - r.top) / r.height;
        el.style.setProperty("--mx", `${(x * 100).toFixed(1)}%`);   // spotlight follows the pointer
        el.style.setProperty("--my", `${(y * 100).toFixed(1)}%`);
        el.style.transform =
          `perspective(900px) rotateX(${((0.5 - y) * 9).toFixed(2)}deg) rotateY(${((x - 0.5) * 11).toFixed(2)}deg) translateY(-6px)`;
      });
      el.addEventListener("mouseleave", () => { el.style.transform = ""; });
    });
  }

  function bindHero() {
    const hero = document.querySelector(".pb-hero");
    const wrap = hero && hero.querySelector(".pb-cube-wrap");
    if (!wrap || hero.dataset.parallax) return;
    hero.dataset.parallax = "1";
    hero.addEventListener("mousemove", (e) => {
      const r = hero.getBoundingClientRect();
      const x = (e.clientX - r.left) / r.width - 0.5;
      const y = (e.clientY - r.top) / r.height - 0.5;
      wrap.style.transform = `rotateX(${(-y * 18).toFixed(2)}deg) rotateY(${(x * 24).toFixed(2)}deg)`;
    });
    hero.addEventListener("mouseleave", () => { wrap.style.transform = ""; });
  }

  function bind() {
    if (reduce || !finePointer) return;
    bindCards();
    bindHero();
  }
  if (window.document$) { window.document$.subscribe(bind); } else { document.addEventListener("DOMContentLoaded", bind); }
})();
