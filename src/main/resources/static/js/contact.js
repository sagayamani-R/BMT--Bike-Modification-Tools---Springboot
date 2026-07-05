document.addEventListener("DOMContentLoaded", function() {
  const form = document.querySelector(".contact-form");
  form.addEventListener("submit", function(e) {
    e.preventDefault();
    alert("Thank you for contacting us! We’ll get back to you soon.");
    form.reset();
  });
});
