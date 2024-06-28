const navGroup = document.getElementById('navGroup');
const menuToggle = document.querySelector('.menu-toggle');
const mobileMenu = document.getElementById('mobile-menu');

document.addEventListener('scroll', hideNavGroup);
document.addEventListener('click', hideNavGroupOutside);

if (menuToggle) {
    menuToggle.addEventListener('click', toggleNavGroup);
}

function toggleNavGroup(event) {
    event.stopPropagation();
    navGroup.classList.toggle('show');
    mobileMenu.classList.toggle('is-active');
}

function hideNavGroup() {
    navGroup.classList.remove('show');
    mobileMenu.classList.remove('is-active');
}

function hideNavGroupOutside(event) {
    if (!navGroup.contains(event.target)) {
        hideNavGroup();
    }
}