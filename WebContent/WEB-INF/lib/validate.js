var LABEL_USERNAME = "Tên đăng nhập";
var LABEL_PASSWORD = "Mật khẩu";
var LABEL_NAME = "Họ tên";
var LABEL_ADDRESS = "Địa chỉ";
var LABEL_PHONE = "Số điện thoại";

var MSG_NUMBERS_ONLY = "Chỉ được nhập số!";
var MSG_ALPHABETS_ONLY = "Chỉ được nhập chữ!";
var MSG_NUMBERS_ALPHABETS_ONLY = "Chỉ nhập chữ hoặc số!";
var MSG_ONLY_ADDRESS = "Chỉ nhập chữ, số, ký tự '/' và ','!";

var MIN_LEN = 6;
var MAX_LEN_ADDRESS = 150;
var MAX_LEN_PHONE = 10;
var MAX_LEN_PRICE = 10;
var MAX_LEN_NAME = 50;

var ONLY_NUMBERS = "numbers only";
var ONLY_ALPHABETS = "alphabets only";
var ONLY_NUMBERS_ALPHABETS = "numbers and alphabets only";
var ONLY_ADDRESS = "address only"; // address includes numbers, alphabets, '/'
// , and ','

function notifyError(obj, msg) {
	clearNotifyError();
	console.log(obj);
	obj.after("<span class='error-message' style='color:red;display:block;font-weight:bold;margin-top:3px;'>" + msg + "</span>");
}

function clearNotifyError() {
	$(".error-message").remove();
}

(function($) {

	function notContainsNumbersOnly(p) {
		// return any characters which is not these characters below
		var result = /[^0-9]/g.test(p);
		return result;
	}

	function notContainsAlphabetsOnly(p) {
		// return any characters which is not these characters below
		var result = /[^a-zA-ZáàạãảâấầậẫẩăắằặẳẵđéèẹẽẻêếềệểễóòọõỏôốồộổỗơớờợỡởúùụũủưứừựữửíìịĩỉýỳỵỹỷAÁÀẠÃẢÂẤẦẬẪẨĂẮẰẶẴẲĐÉÈẸẼẺÊẾỀỆỄỂÓÒỌÕỎÔỐỒỘỖỔƠỚỜỢỠỞÚÙỤŨỦƯỨỪỰỮỬÍÌỊĨỈÝỲỴỸỶ ]/g
				.test(p);
		return result;
	}

	function notContainsNumbersAlphabetsCommaOnly(p) {
		// return any characters which is not these characters below
		var result = /[^a-zA-Z0-9áàạãảâấầậẫẩăắằặẳẵđéèẹẽẻêếềệểễóòọõỏôốồộổỗơớờợỡởúùụũủưứừựữửíìịĩỉýỳỵỹỷAÁÀẠÃẢÂẤẦẬẪẨĂẮẰẶẴẲĐÉÈẸẼẺÊẾỀỆỄỂÓÒỌÕỎÔỐỒỘỖỔƠỚỜỢỠỞÚÙỤŨỦƯỨỪỰỮỬÍÌỊĨỈÝỲỴỸỶ,.\/ ]/g
				.test(p);
		return result;
	}

	function containsInvalidCharacters(p) {
		// return any characters which is not these characters below
		var result = /[^A-Za-z0-9áàạãảâấầậẫẩăắằặẳẵđéèẹẽẻêếềệểễóòọõỏôốồộổỗơớờợỡởúùụũủưứừựữửíìịĩỉýỳỵỹỷAÁÀẠÃẢÂẤẦẬẪẨĂẮẰẶẴẲĐÉÈẸẼẺÊẾỀỆỄỂÓÒỌÕỎÔỐỒỘỖỔƠỚỜỢỠỞÚÙỤŨỦƯỨỪỰỮỬÍÌỊĨỈÝỲỴỸỶ ]/g
				.test(p);
		return result;
	}

	$.fn.validateInput = function(length, criteria) {
		var input = this.val();
		var len = input.length;

		if (input == "" || len < length) {
			notifyError(this, "Phải có ít nhất " + length + " ký tự!");
			return false;
		}
		if (criteria == ONLY_NUMBERS) {
			if (notContainsNumbersOnly(input)) {
				notifyError(this, MSG_NUMBERS_ONLY);
				return false;
			}
		}
		if (criteria == ONLY_ALPHABETS) {
			if (notContainsAlphabetsOnly(input)) {
				notifyError(this, MSG_ALPHABETS_ONLY);
				return false;
			}
		}
		if (criteria == ONLY_NUMBERS_ALPHABETS) {
			if (containsInvalidCharacters(input)) {
				notifyError(this, MSG_NUMBERS_ALPHABETS_ONLY);
				return false;
			}
		}
		if (criteria == ONLY_ADDRESS) {
			if (notContainsNumbersAlphabetsCommaOnly(input)) {
				notifyError(this, MSG_ONLY_ADDRESS);
				return false;
			}
		}
		return true;
	}

	$.fn.verifyInputRealtime = function() {
		this.keypress(function(e) {
			clearNotifyError();
			var key = String.fromCharCode(e.which);
			console.log("key=" + key);
			var len = $(this).val().length;
			var id = $(this).attr("id");

			if (len == 0) {
				if (key == 32) {
					e.preventDefault();
				}
			}
			
			if (id.includes("name")) {
				if (len >= MAX_LEN_PHONE) {
					notifyError($(this), "Nhập tối đa " + MAX_LEN_NAME + " ký tự!");
					e.preventDefault();
				}
			}

			// verify input for phone numbers
			if (id.includes("phone")) {
				if (len == 0) {
					if (key != '0') {
						notifyError($(this), "Phải bắt đầu bằng số 0!");
						e.preventDefault();
					}
				}
				if (len >= MAX_LEN_PHONE) {
					notifyError($(this), "Nhập tối đa " + MAX_LEN_PHONE + " số!");
					e.preventDefault();
				}
				if (notContainsNumbersOnly(key)) {
					notifyError($(this), MSG_NUMBERS_ONLY);
					e.preventDefault();
				}
			}
			
			if (id.includes("price")) {
				if (len == 0) {
					if (key == '0') {
						notifyError($(this), "Không được bắt đầu bằng số 0!");
						e.preventDefault();
					}
				}
				if (len >= MAX_LEN_PRICE) {
					notifyError($(this), "Không nhập quá " + MAX_LEN_PRICE + " số!");
					e.preventDefault();
				}
				if (notContainsNumbersOnly(key)) {
					notifyError($(this), MSG_NUMBERS_ONLY);
					e.preventDefault();
				}
			}
		});
	}

}(jQuery));