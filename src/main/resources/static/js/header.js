const navGroup = document.getElementById('navGroup');
// Get the .menu-toggle element
const menuToggle = document.querySelector('.menu-toggle');
// Get the #mobile-menu element
const mobileMenu = document.getElementById('mobile-menu');

// Add event listeners to hide .nav-group on scroll and outside click
document.addEventListener('scroll', hideNavGroup);
document.addEventListener('click', hideNavGroupOutside);

// Check if menuToggle is not null before adding the event listener
if (menuToggle) {
    // Add click event listener to the .menu-toggle element
    menuToggle.addEventListener('click', toggleNavGroup);
}

function toggleNavGroup(event) {
    // Stop the click event from propagating to the document
    event.stopPropagation();
    navGroup.classList.toggle('show');
    mobileMenu.classList.toggle('is-active'); // Add or remove is-active class
}

function hideNavGroup() {
    navGroup.classList.remove('show');
    mobileMenu.classList.remove('is-active'); // Remove is-active class
}

function hideNavGroupOutside(event) {
    // Check if the clicked element is outside .nav-group
    if (!navGroup.contains(event.target)) {
        hideNavGroup();
    }
}

// const toggleButton = document.querySelector('.menu-toggle');
// const navGroup = document.querySelector('.nav-group');
//
// toggleButton.addEventListener('click', () => {
//     navGroup.classList.toggle('show');
// });
//
// const mobileMenu = document.querySelector('#mobile-menu');
// mobileMenu.onclick = function() {
//     toggleButton.click();
// };
