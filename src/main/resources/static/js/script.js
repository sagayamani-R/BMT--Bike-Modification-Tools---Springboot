console.log("BMT app loaded");
let currentPage = 0;

function loadReviews(direction) {
  if (direction === "next") {
    currentPage++;
  } else if (direction === "prev" && currentPage > 0) {
    currentPage--;
  }

  fetch(`/api/reviews?page=${currentPage}&size=4`)
    .then((res) => {
      if (!res.ok) {
        throw new Error(`Server returned ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      const container = document.getElementById("review-container");
      container.innerHTML = "";
      data.forEach((review) => {
        container.innerHTML += `
          <div class="col-3 review-card">
            <p>${review.comment}</p>
            <div class="rating">
              ${'<i class="fa fa-star"></i>'.repeat(review.fullStars)}
              ${review.halfStar ? '<i class="fa fa-star-half-o"></i>' : ""}
              ${'<i class="fa fa-star-o"></i>'.repeat(review.emptyStars)}
            </div>
            <div class="review-user">
              <img src="${review.img}" alt="User image">
              <h3>${review.decryptedUsername}</h3>
            </div>
          </div>`;
      });
    })
    .catch((err) => console.error("Error loading reviews:", err));
}

// Event delegation: listens on document, catches clicks inside buttons
document.addEventListener("click", (e) => {
  if (e.target.closest("#arrow-left")) {
    console.log("Left arrow clicked");
    loadReviews("prev");
  }
  if (e.target.closest("#arrow-right")) {
    console.log("Right arrow clicked");
    loadReviews("next");
  }
});


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
