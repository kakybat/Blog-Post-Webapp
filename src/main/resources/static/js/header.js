const navGroup = document.getElementById('navGroup');
const menuToggle = document.querySelector('.menu-toggle');


if (menuToggle) {
    menuToggle.addEventListener('click', () =>{
        navGroup.classList.toggle('show');
        menuToggle.classList.toggle('is-active');
    });
}

document.addEventListener('click', (e) =>{
    if (!navGroup.contains(e.target) && e.target !== menuToggle){
        navGroup.classList.remove('show');
        menuToggle.classList.remove('is-active');
    }
});

window.addEventListener('scroll', () => {
    navGroup.classList.remove('show');
    menuToggle.classList.remove('is-active');
});