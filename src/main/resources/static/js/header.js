// Update cart count on page load
(async function updateCartCount() {
  try {
    const res = await fetch('/api/cart/count');
    const count = await res.text();
    document.getElementById('bmt-cart-count').textContent = count;
  } catch (err) {
    console.error("Failed to update cart count", err);
  }
})();

// Attach event listeners to Add to Cart buttons
document.querySelectorAll(".add-to-cart-btn").forEach(btn => {
  btn.addEventListener("click", async e => {
    e.preventDefault();
    const productId = btn.dataset.productId;

    try {
      const res = await fetch(`/cart/add/${productId}/json?qty=1`, { method: "POST" });
      const data = await res.json();

      if (data.success) {
        // Update badge count immediately
        document.getElementById('bmt-cart-count').textContent = data.count;
      }
    } catch (err) {
      console.error("Failed to add item to cart", err);
    }
  });
});
