<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="cpath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>LogIn</title>
    <meta name="author" content="Soon9">
    <meta name="description" content="Soon9's web studio">
    <meta name="viewport"
        content="width=device-width, initial-scale=1, user-scalable=no, maximum-scale=1, minimum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">

    <!-- css -->
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/main-responsive.css">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/my.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>

<body>
    <jsp:include page="../header/header.jsp" />
    <main class="login-body slider-bg">
        <div class="register-form">
            <h2>Register</h2>
            <div class="form-input">
                <label for="name">Email Address</label>
                <input type="email" name="userEmail" id="memEmail" placeholder="Email Address">
                <span id="memEmailRight"></span>
            </div>
            <div class="form-input">
                <label for="name">Nick Name</label>
                <input type="text" name="userNickName" id="userNickName" placeholder="NickName">
                <span id="memNickNameRight"></span>
            </div>
            <div class="form-input">
                <label for="name">Password</label>
                <input type="password" name="userPw" id="memPassword" placeholder="Password">
                <span id="memPasswordRight"></span>
            </div>
            <div class="form-input">
                <label for="name">Confirm Password</label>
                <input type="password" name="userPw-confirm" id="memPasswordCheck" placeholder="Confirm Password">
                <span id="memPasswordCheckRight"></span>
            </div>
            <div class="form-input">
                <input type="button" name="submit" value="Registration" id="signUpBtn">
            </div>
        </div>
    </main>
    <script src="/js/bootstrap.min.js"></script>

    <script>
        let flag1 = false;
        let flag2 = false;
        let flag3 = false;
        let flag4 = false;

        const $memEmail = document.getElementById('memEmail');
        const $memEmailRight = document.getElementById('memEmailRight');

        const $memPassword = document.getElementById('memPassword');
        const $memPasswordRight = document.getElementById('memPasswordRight');

        const $memPasswordCheck = document.getElementById('memPasswordCheck');
        const $memPasswordCheckRight = document.getElementById('memPasswordCheckRight');

        const $memNickName = document.getElementById('userNickName')
        const $memNickNameRight = document.getElementById('memNickNameRight');

        const $signUpBtn = document.getElementById('signUpBtn');

        $memEmail.onkeyup = function () {
            const emailRegex = /^[a-z0-9\.\-_]+@([a-z0-9\-]+\.)+[a-z]{2,6}$/;

            if (!$memEmail.value) {
                $memEmailRight.innerHTML = '아이디는 필수로 입력해야 합니다.';
                $memEmailRight.style.color = '#f21a3f';
            } else if (!emailRegex.test($memEmail.value)) {
                $memEmailRight.innerHTML = '이메일 형식에 맞춰서 입력해 주세요.';
                $memEmailRight.style.color = '#f21a3f';

            } else {
                $memEmailRight.innerHTML = '';
                fetch(`/member/check?memEmail=` + $memEmail.value)
                    .then(res => res.json())
                    .then(flag => {
                        if (flag) {
                            $memEmailRight.innerHTML = '중복된 이메일 입니다.';
                            $memEmailRight.style.color = '#f21a3f';

                        } else {
                            $memEmailRight.innerHTML = '가입 할 수 있는 이메일 입니다.';
                            $memEmailRight.style.color = '#0de5c0';
                            flag1 = true;
                        }
                    });
            }
        }

        $memPassword.onkeyup = function () {
            const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,20}$/;
            if (!$memPassword.value) {
                $memPasswordRight.innerHTML = '비밀번호는 필수값입니다!';
                $memPasswordRight.style.color = '#f21a3f';
            } else if (!pwRegex.test($memPassword.value)) {
                $memPasswordRight.innerHTML = '8글자 이상의 영문,숫자,특수문자를 포함해주세요!';
                $memPasswordRight.style.color = '#f21a3f';
            } else {
                $memPasswordRight.innerHTML = '사용 가능한 비밀번호입니다.';
                $memPasswordRight.style.color = '#0de5c0';
                flag2 = true;
            }
        };

        $memPasswordCheck.onkeyup = function () {
            if (!$memPasswordCheck.value) { //패스워드 안적은 상황
                $memPasswordCheckRight.innerHTML = '비밀번호는 필수값입니다!';
                $memPasswordCheckRight.style.color = '#f21a3f';
            } else if ($memPasswordCheck.value !== $memPassword.value) {
                $memPasswordCheckRight.innerHTML = '1차 비밀번호와 일치하게 작성해주세요!';
                $memPasswordCheckRight.style.color = '#f21a3f';
            } else {
                $memPasswordCheckRight.innerHTML = '비밀번호가 일치합니다.';
                $memPasswordCheckRight.style.color = '#0de5c0';
                flag3 = true;
            }
        };

        $memNickName.onkeyup = function () {
            if (!$memNickName.value) {
                memNickNameRight.innerHTML = '닉네임은 필수값입니다!';
                $memNickNameRight.style.color = '#f21a3f';
            } else {
                $memNickNameRight.innerHTML = '사용 가능한 닉네임 입니다.';
                $memNickNameRight.style.color = '#0de5c0';
                flag4 = true;
            }
        }

        $signUpBtn.onclick = function () {
            if (flag1 && flag2 && flag3 && flag4) {
                const userValue = {
                    memEmail: $memEmail.value,
                    memPassword: $memPassword.value,
                    memNickName : $memNickName.value
                }
                fetch('/member/register.do', {
                    method: 'POST',
                    headers: {
                        'content-type': 'application/json'
                    },
                    body: JSON.stringify(userValue)
                })
                    .then(res => {
                        if (res.status === 200) {
                            alert('회원가입을축하합니다.');
                            window.location.href = '/member/logIn.do';
                        } else {
                            alert('회원가입에 실패했습니다. 잠시 후 다시 시도해주세요.');
                        }
                    });
            }
        }
    </script>
</body>

</html>