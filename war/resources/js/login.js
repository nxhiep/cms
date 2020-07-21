function onLogin(button) {
	let loginDialog = $('#login-panel');
	let account = loginDialog.find('#log-account').val();
	let password = loginDialog.find('#log-password').val();
	
	if(!account){
		alert('account')
		return;
	}
	if(!password){
		alert('password');
		return;
	}
	$(button).addClass('.loading-css');
	password = encryptPassword(account, password);
	$.post('/api/login', { account: account, password: password }, function(user) {
		loginStatus(user); 
		$(button).removeClass('.loading-css');
	});
}

function onRegister(button) {
	let loginDialog = $('#register-panel');
	let account = loginDialog.find('#res-account').val();
	let email = loginDialog.find('#res-email').val();
	let phoneNumber = loginDialog.find('#res-phone-number').val();
	let password = loginDialog.find('#res-password').val();
	let rePassword = loginDialog.find('#res-repassword').val();
	
	if(!account){
		alert('account')
		return;
	}
	if(!email){
		alert('email');
		return;
	}
	if(!phoneNumber){
		alert('phoneNumber')
		return;
	}
	if(!password){
		alert('password');
		return;
	}
	if(password != rePassword){
		alert('rePassword');
		return;
	}
	$(button).addClass('.loading-css');
	password = encryptPassword(account, password);
	$.post('/api/register', { account: account, email: email, phoneNumber: phoneNumber, password: password, rePassword: rePassword }, function(user) {
		loginStatus(user); 
		$(button).removeClass('.loading-css');
	});
}

function encryptPassword(account, password) {
	var key = getMd5(account.toLowerCase() +"."+password);
	return encryptByDES(password, key);
}

function loginStatus(user) {
	let status = user ? user.loginStatus : Config.LOGIN_FAILED;
	switch(status) {
		case Config.LOGIN_SUCCESS: 
			console.log('LOGIN_SUCCESS');
			Cookies.setCookie(Config.COOKIE_SESSION_KEY, user.sessionId, Config.EXPIRED_TIME_LOGIN);
			window.location.reload();
			break;
		case Config.LOGIN_ACCOUNT_IS_USED: 
			console.log('LOGIN_ACCOUNT_IS_USED');
			break;
		case Config.LOGIN_ACCOUNT_NOT_EXIST: 
			console.log('LOGIN_ACCOUNT_NOT_EXIST');
			break;
		case Config.LOGIN_WRONG_PASSWORD: 
			console.log('LOGIN_WRONG_PASSWORD');
			break;
		case Config.LOGIN_WRONG_PROVIDER: 
			console.log('LOGIN_WRONG_PROVIDER');
			break;
		case Config.LOGIN_ACCOUNT_NOT_ACTIVATED: 
			console.log('LOGIN_ACCOUNT_NOT_ACTIVATED');
			break;
		case Config.LOGIN_MOBILE_IS_USED: 
			console.log('LOGIN_MOBILE_IS_USED');
			break;
		default: 
			console.log('LOGIN_FAILED');
			break;
	}
}