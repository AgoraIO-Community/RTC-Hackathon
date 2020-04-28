import vPinyin from './pinyin2.js';
var Letters =  ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U','V', 'W', 'X', 'Y', 'Z', '#'];
export default {
	paixu: function(ary) {		
		var e = []
		e = ary
		/*按字母排序分开*/
		var list = []
		e.forEach((value,index)=>{
			var pinyin = vPinyin.chineseToPinYin(value.name)[0]
			Letters.forEach((value2, index2) => {
				if (value2 == pinyin) {
					var contact = {
						letter: value2,
						value: {
							name: value.name,
							phone: value.phone.replace(/\s*/g,""),
						}
					}
					list = list.concat(contact)
				}
			})
		})		
		/*获取所有字母字母去掉*/
		var list2 = []
		list.forEach((value, index) => {
			var map = list[index].letter
			list2 = list2.concat(map)
		})		
		/*去掉相同的字母*/
		var newArr = [];
		for (var i = 0; i < list2.length - 1; i++) {
			if (newArr.indexOf(list2[i]) == -1) {
				newArr.push(list2[i]);
			}
		}
		/*给数组letter赋值字母*/
		var list3 = []
		newArr.forEach((value, index) => {
			var map = {
				letter: value,
				contacts: []
			}
			list3 = list3.concat(map)
		})
		/*得到数组*/
		list3.forEach((value, index) => {
			list.forEach((value2, index2) => {
				if (value.letter == value2.letter) {
					var map = value2.value
					value.contacts = value.contacts.concat(map)
				}
			})
		})
		return list3;
	}
}
