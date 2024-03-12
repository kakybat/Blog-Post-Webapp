let currentSlide = 0;
const sliderWrapper = document.getElementById('sliderWrapper');
const totalSlider = document.querySelectorAll('.slider-box').length;
const slidesToShow = 3;

function showSlide(index){
    if( index >= totalSlider -slidesToShow + 1){
        currentSlide = 0;
    } else if (index < 0){
        currentSlide = totalSlider -slidesToShow;
    } else {
        currentSlide = index;
    }

    const translateValue = -currentSlide * 340;
    sliderWrapper.style.transform = `translateX(${translateValue}px)`;
}
function nextSlide(){
    showSlide(currentSlide + 1);
}
function prevSlide(){
    showSlide(currentSlide - 1);
}
document.getElementById('nextBtn').addEventListener('click', nextSlide);
document.getElementById('prevBtn').addEventListener('click', prevSlide);

setInterval(() => {
    nextSlide();
}, 3000);