jQuery(document).ready(function ($) {
	checkAvatar();
});

function checkAvatar(element, log) {
	log && console.clear();
	log && console.log('checkAvatar', element);
	let defaultAvatarUrl = '/resources/images/default_avatar.png';
	if(typeof currentTime === 'undefined'){
		currentTime = new Date().getTime();
	}
	
	$.each(element ? $(element) : $('.img-avatar'), function(index, item){
		item = $(item);
		if(item.children().length > 0){
			return;
		}
		let img = $('<img />');
		item.append(img);
		let userId = item.data('id');
		let dfAvatar = item.data('root-url');
		if(!dfAvatar){
			dfAvatar = defaultAvatarUrl;
		}
		log && console.log('checkAvatar item userId', userId);
		if(userId){
			if(isFacebookId(userId)){
				avatarUrl = "https://graph.facebook.com/" + userId +"/picture?width=100&redirect=false";
			} else {
				avatarUrl = 'https://storage.googleapis.com/' + Config.BUCKET_NAME + "/images/"+userId+"?t="+currentTime+"&ignoreCache=1";
			}
			img.attr('src', avatarUrl);
			img.off('error').on('error', function () {
				if($(this).attr('src').indexOf(dfAvatar) < 0){
					$(this).attr('src', dfAvatar);
				}
			});
		} else {
			img.attr('src', dfAvatar);
		}
		try {
			img.off('load').on('load', function() {
				item.addClass('loaded');
			});
		} catch(e){
			setTimeout(function(){
				item.addClass('loaded');
			}, 5000);
		}
	});
}
function isFacebookId(str){
	str = "" + str;
	return str && !!str.match(/^\d+$/) && str.length > 15;
}

function getMd5(s) {
	var hash = md5(s);
	return hash;
}

function encryptByDES(message, key) {
	var keyHex = CryptoJS.enc.Utf8.parse(key);
	var encrypted = CryptoJS.DES.encrypt(message, keyHex, {
		mode : CryptoJS.mode.ECB,
		padding : CryptoJS.pad.Pkcs7
	});
	return encrypted.toString();
}

function decryptByDES(ciphertext, key) {
    var keyHex = CryptoJS.enc.Utf8.parse(key);
    // direct decrypt ciphertext
    var decrypted = CryptoJS.DES.decrypt({
        ciphertext: CryptoJS.enc.Base64.parse(ciphertext)
    }, keyHex, {
        mode: CryptoJS.mode.ECB,
        padding: CryptoJS.pad.Pkcs7
    });
    return decrypted.toString(CryptoJS.enc.Utf8);
}

class Cookies {
	static getCookie(cname) {
		let name = cname + "=";
		let decodedCookie = decodeURIComponent(document.cookie);
		let ca = decodedCookie.split(';');
		for(var i = 0; i <ca.length; i++) {
			let c = ca[i];
		    while (c.charAt(0) == ' ') {
		      c = c.substring(1);
		    }
		    if (c.indexOf(name) == 0) {
		      return c.substring(name.length, c.length);
		    }
		}
		return null;
	}
	static setCookie(cname, cvalue, exdays) {
		let d = new Date();
		d.setTime(d.getTime() + ((exdays ? exdays : 1)*24*60*60*1000));
		let expires = "expires="+ d.toUTCString();
		document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
	}
	static deleteCookie(cname) {
		document.cookie = cname+"=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
	}
}