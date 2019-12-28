/**
 * 
 * Apply to HTML text. Ex: <span class="price">6000000</span> Developed by
 * cstoigian.blogspot.com, Sat, 21/09/2019.
 * 
 */

(function($) {

	function formatMoney(m) {
		return m.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,');
	}

	$.fn.moneyVND = function() {
		var m = "";
		var unit = "đ";
		var isInput = true;

		// is input or span, div, textara .etc...
		if (this.val() == "" || this.val() == null) {
			isInput = false;
		}

		if (isInput) {
			m = this.val();
		} else {
			m = this.text();
		}
		m = formatMoney(m);
		if (isInput) {
			this.val(m + unit);
		} else {
			this.text(m + unit);
		}
	}

}(jQuery));

function formatMoneyVND() {
	console.log("Formatting money by VND...");
	$(".price").each(function() {
		var m = $(this).text();
		console.log("money=" + m);
		$(this).moneyVND();
	});
}