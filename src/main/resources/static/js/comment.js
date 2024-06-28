document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('commentForm');
    if (form) {
        form.addEventListener('submit', function(e) {
            // Capture current scroll position
            const scrollPosition = window.scrollY || document.documentElement.scrollTop;

            // Optionally, you can handle additional form submission logic here
            // For example, submit form via AJAX if needed
            // Example:
            // fetch(form.action, {
            //     method: 'POST',
            //     body: new FormData(form)
            // }).then(response => {
            //     // Handle response as needed
            // }).catch(error => {
            //     console.error('Error submitting form:', error);
            // });

            // Allow default form submission to proceed
            // (assuming synchronous submission is required for Spring Boot backend logic)
            // If you're using AJAX, replace this with your AJAX submission logic
            // form.submit();

            // Preserve scroll position after a small delay to ensure form submission proceeds
            setTimeout(() => {
                window.scrollTo(0, scrollPosition);
            }, 100); // Adjust delay as needed
        });
    }
});