<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>
		<jsp:include page="/jsp/widgets/seo.jsp"></jsp:include>
		<jsp:include page="/jsp/widgets/resources.jsp"></jsp:include>
		<style>
			.main-panel {
			width: 100%;
			height: 100%;
			background-color: grey;
			}
			.login-center-panel {
			background-color: white;
			border-radius: 10px;
			padding: 20px;
			}
			.button-anchor {
			background: none;
			border: none;
			outline: none;
			color: #3F51B5;
			margin: 0 auto;
			}
			.button-anchor:hover {
			text-decoration: underline;
			}
			.login-center-panel label {
		    margin: 0;
		    width: 100px;
			}
			.login-center-panel .item {
		    display: flex;
		    align-items: center;
		    margin-bottom: 10px;
			}
			.login-center-panel .item.item-title {
			font-weight: bold;
			font-size: 1.2em;
			margin-bottom: 10px
			}
			.login-center-panel input {
		    border: 1px solid #ddd;
		    padding: 7px;
		    border-radius: 3px;
		    width: 200px;
			}
			.login-center-panel .button-login {
		    margin: 10px auto 0 auto;
			}
		</style>
	</head>
<body>
	<div class="main-panel flex flex-ai-center flex-jc-center">
		<div class="login-center-panel">
			<div id="login-panel" style="display:none;">
				<div class="item item-title">
					ĐĂNG NHẬP
				</div>
				<div class="item">
					<label for="log-account">Tài khoản</label>
					<input 
					name="log-account" 
					id="log-account"
					placeholder="Tài khoản (*)" type="text" 
					autocomplete="username" 
					>
				</div>
				<div class="item">
					<label for="log-password">Mật khẩu</label>
					<input 
					name="log-password" 
					id="log-password"
					placeholder="Mật khẩu (*)"
					type="password" 
					autocomplete="new-password" 
					onkeydown="if(event.keyCode == 13){onLogin('#button-login');return false;}"
					>
				</div>
				<div class="item">
					<button id="button-login" onclick="onLogin(this)" class="button-login btn btn-success">Đăng nhập</button>
<!-- 					<button onclick="onLoginFacebook(this)" class="button-facebook">Đăng nhập Facebook</button> -->
				</div>
				<div class="item">
					<button onclick="openRegister()" class="button-anchor">Đăng ký</button>
				</div>
			</div>
			<div id="register-panel" style="display:none;">
				<div class="item">
					ĐĂNG KÝ
				</div>
				<div class="item">
					<label for="res-account">Tài khoản</label>
					<input 
					name="res-account" 
					id="res-account"
					placeholder="Tài khoản (*)" type="text" 
					autocomplete="username" 
					>
				</div>
				<div class="item">
					<label for="res-email">Email</label>
					<input 
					name="res-email" 
					id="res-email"
					placeholder="Email (*)" type="text" 
					autocomplete="email" 
					>
				</div>
				<div class="item">
					<label for="phoneNumber">Số điện thoại</label>
					<input 
					name="res-phone-number" 
					id="res-phone-number"
					placeholder="Số điện thoại" type="text" 
					autocomplete="mobile" 
					>
				</div>
				<div class="item">
					<label for="res-password">Mật khẩu</label>
					<input 
					name="res-password" 
					id="res-password"
					placeholder="Mật khẩu (*)" 
					type="password" 
					autocomplete="new-password" 
					>
				</div>
				<div class="item">
					<label for="res-repassword">Nhập lật khẩu</label>
					<input 
					name="res-repassword" 
					id="res-repassword"
					placeholder="Nhập lại mật khẩu (*)"
					type="password" 
					autocomplete="new-password" 
					>
				</div>
				<div class="item">
					<button onclick="onRegister(this)" class="button-login btn btn-success">Đăng ký</button>
<!-- 					<button onclick="onLoginFacebook(this)" class="button-facebook">Đăng nhập Facebook</button> -->
				</div>
				<div class="item">
					<button onclick="openLogin()" class="button-anchor">Đăng nhập</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
	openLogin();
});
function openLogin() {
	$('#login-panel').show();
	$('#register-panel').hide();
}

function openRegister() {
	$('#login-panel').hide();
	$('#register-panel').show();
}
</script>