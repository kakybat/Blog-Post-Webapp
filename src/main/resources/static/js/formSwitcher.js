function toggleForm(){
    const loginForm = document.getElementById('loginForm');
    const signupContainer = document.getElementById('signupContainer');

    if(loginForm.style.display === 'none'){
        loginForm.style.display = 'block';
        signupContainer.style.display = 'none'
    } else {
        loginForm.style.display = 'none';
        signupContainer.style.display = 'block';
    }
}