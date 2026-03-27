// User Registration
function registerUser() {
    const userData = {
        name: $('#name').val(),
        email: $('#email').val(),
        password: $('#password').val(),
        bio: $('#bio').val()
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/auth/register",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(userData),
        success: function(res) {
            alert(res.message);
            window.location.href = "login.html";
        },
        error: function(err) {
            alert("Registration Failed!");
        }
    });
}

function loginUser() {
    const authData = {
        email: $('#loginEmail').val(),
        password: $('#loginPassword').val()
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/auth/login",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(authData),
        success: function (res) {
            console.log("Full Response:", res);

            if (res.code === 200 || res.code === 201) {
                const token = res.data.access_token;
                localStorage.setItem('token', token);
                localStorage.setItem('userId', String(res.data.userId));
                localStorage.setItem('userName', res.data.userName);


                console.log("Token saved:", localStorage.getItem('token'));
                console.log("UserId saved:", localStorage.getItem('userId'));

                window.location.href = "../index.html";
            }
        }
       ,
        error: function(err) {
            console.log("Login Error:", err);
            alert("Invalid Credentials!");
        }
    });
}