<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
        </div>
        <button id="btn-login" class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=017676ab3a5765d55919325589160cf0&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img
                height="38px" src="/images/kakao_login_button.png"/></a>
    </form>
</div>

<%@ include file="../layout/footer.jsp" %>

