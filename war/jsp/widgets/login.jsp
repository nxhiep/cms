<%@page import="com.hiepnx.cms.shared.model.UserInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
UserInfo currentUser = (UserInfo) request.getAttribute("currentUser");
%>
<div class="login-panel">
<%if(!(currentUser != null && currentUser.isLogedIn())){ %>
<div class="modal fade" id="login-register-modal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-body">
				<div>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="item"></div>
				<div class="item"></div>
				<div id="login-dialog" style="display:none;">
					<div class="item">
						<label for="log-account">Tài khoản</label>
						<input 
						name="log-account" 
						id="log-account"
						placeholder="Tài khoản (*)" type="text" 
						autocomplete="name" 
						>
					</div>
					<div class="item">
						<label for="log-password">Mật khẩu</label>
						<input 
						name="log-password" 
						id="log-password"
						placeholder="Mật khẩu (*)" type="text" 
						autocomplete="name" 
						onkeydown="if(event.keyCode == 13){onLogin('#button-login');return false;}"
						>
					</div>
					<div class="item">
						<button id="button-login" onclick="onLogin(this)" class="button-login">Đăng nhập</button>
<!-- 						<button onclick="onLoginFacebook(this)" class="button-facebook">Đăng nhập Facebook</button> -->
					</div>
				</div>
				<div id="register-dialog" style="display:none;">
					<div class="item">
						<label for="res-account">Tài khoản</label>
						<input 
						name="res-account" 
						id="res-account"
						placeholder="Tài khoản (*)" type="text" 
						autocomplete="name" 
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
						autocomplete="tel" 
						>
					</div>
					<div class="item">
						<label for="res-password">Mật khẩu</label>
						<input 
						name="res-password" 
						id="res-password"
						placeholder="Mật khẩu (*)" type="text" 
						autocomplete="name" 
						>
					</div>
					<div class="item">
						<label for="res-repassword">Nhập lật khẩu</label>
						<input 
						name="res-repassword" 
						id="res-repassword"
						placeholder="Nhập lại mật khẩu (*)" type="text" 
						autocomplete="name" 
						>
					</div>
					<div class="item">
						<button onclick="onRegister(this)" class="button-login">Đăng ký</button>
<!-- 						<button onclick="onLoginFacebook(this)" class="button-facebook">Đăng nhập Facebook</button> -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function openLoginDialog() {
		$('#login-dialog').show();
		$('#register-dialog').hide();
		$('#login-register-modal').modal();
	}
	function openRegisterDialog() {
		$('#register-dialog').show();
		$('#login-dialog').hide();
		$('#login-register-modal').modal();
	}
</script>
<%} %>
</div>