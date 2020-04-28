(global["webpackJsonp"] = global["webpackJsonp"] || []).push([["pages/user/account_security/verification_phone"],{

/***/ 396:
/*!*********************************************************************************************************************!*\
  !*** F:/demo/reference/food/HX-SHOP 外卖商户模板/main.js?{"page":"pages%2Fuser%2Faccount_security%2Fverification_phone"} ***!
  \*********************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(createPage) {__webpack_require__(/*! uni-pages */ 4);__webpack_require__(/*! @dcloudio/uni-stat */ 5);
var _vue = _interopRequireDefault(__webpack_require__(/*! vue */ 2));
var _verification_phone = _interopRequireDefault(__webpack_require__(/*! ./pages/user/account_security/verification_phone.vue */ 397));function _interopRequireDefault(obj) {return obj && obj.__esModule ? obj : { default: obj };}
createPage(_verification_phone.default);
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-toutiao/dist/index.js */ 1)["createPage"]))

/***/ }),

/***/ 397:
/*!************************************************************************************************!*\
  !*** F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue ***!
  \************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./verification_phone.vue?vue&type=template&id=13b170d3&scoped=true& */ 398);
/* harmony import */ var _verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./verification_phone.vue?vue&type=script&lang=js& */ 400);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var _verification_phone_vue_vue_type_style_index_0_id_13b170d3_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./verification_phone.vue?vue&type=style&index=0&id=13b170d3&scoped=true&lang=css& */ 402);
/* harmony import */ var _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/runtime/componentNormalizer.js */ 204);

var renderjs





/* normalize component */

var component = Object(_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_runtime_componentNormalizer_js__WEBPACK_IMPORTED_MODULE_3__["default"])(
  _verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_1__["default"],
  _verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__["render"],
  _verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"],
  false,
  null,
  "13b170d3",
  null,
  false,
  _verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__["components"],
  renderjs
)

component.options.__file = "F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue"
/* harmony default export */ __webpack_exports__["default"] = (component.exports);

/***/ }),

/***/ 398:
/*!*******************************************************************************************************************************************!*\
  !*** F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue?vue&type=template&id=13b170d3&scoped=true& ***!
  \*******************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./verification_phone.vue?vue&type=template&id=13b170d3&scoped=true& */ 399);
/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "render", function() { return _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__["render"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__["staticRenderFns"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__["recyclableRender"]; });

/* harmony reexport (safe) */ __webpack_require__.d(__webpack_exports__, "components", function() { return _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_templateLoader_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_16_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_template_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_uni_app_loader_page_meta_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_template_id_13b170d3_scoped_true___WEBPACK_IMPORTED_MODULE_0__["components"]; });



/***/ }),

/***/ 399:
/*!*******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/templateLoader.js??vue-loader-options!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--16-0!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/template.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-uni-app-loader/page-meta.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue?vue&type=template&id=13b170d3&scoped=true& ***!
  \*******************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! exports provided: render, staticRenderFns, recyclableRender, components */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "render", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "staticRenderFns", function() { return staticRenderFns; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "recyclableRender", function() { return recyclableRender; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "components", function() { return components; });
var components = {
  "hx-navbar": function() {
    return __webpack_require__.e(/*! import() | components/hx-navbar/hx-navbar */ "components/hx-navbar/hx-navbar").then(__webpack_require__.bind(null, /*! @/components/hx-navbar/hx-navbar.vue */ 493))
  }
}
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
}
var recyclableRender = false
var staticRenderFns = []
render._withStripped = true



/***/ }),

/***/ 400:
/*!*************************************************************************************************************************!*\
  !*** F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue?vue&type=script&lang=js& ***!
  \*************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./verification_phone.vue?vue&type=script&lang=js& */ 401);
/* harmony import */ var _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_babel_loader_lib_index_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_12_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_script_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_script_lang_js___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 401:
/*!********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/babel-loader/lib!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--12-1!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/script.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue?vue&type=script&lang=js& ***!
  \********************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

"use strict";
/* WEBPACK VAR INJECTION */(function(uni) {Object.defineProperty(exports, "__esModule", { value: true });exports.default = void 0; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
var _default =
{

  watch: {
    maxlength: {
      immediate: true,
      handler: function handler(newV) {
        if (newV === 6) {
          this.ranges = [1, 2, 3, 4, 5, 6];
        } else {
          this.ranges = [1, 2, 3, 4];
        }
      } } },


  data: function data() {
    return {
      codeIndex: 1,
      mobile: '',
      codeArr: [],
      ranges: [1, 2, 3, 4],
      maxlength: 4,
      isPwd: false,
      type: "bottom",
      timing: 0,
      tNumber: 90,
      postStatus: false };

  },
  onLoad: function onLoad(option) {
    var reg = /^1[3-9]\d{9}$/;
    var that = this;

    if (!reg.exec(option.mobile)) {
      uni.showToast({
        icon: 'none',
        title: '手机号格式不正确！',
        duration: 2000 });

    } else {
      this.mobile = option.mobile;
      that.resend();
    }


  },
  methods: {
    getVal: function getVal(e) {var
      value = e.detail.value;
      var arr = value.split('');
      this.codeIndex = arr.length + 1;
      this.codeArr = arr;
      if (this.codeIndex > Number(this.maxlength)) {
        //验证码输入完成以后的操作
        var code = this.codeArr.join('');
        console.log(code);
        this.updatePhone(code);
      }
    },
    //验证手机号
    updatePhone: function updatePhone(c) {
      var that = this;
      uni.showLoading({ mask: true });

      this.$api.user.updatePhone({
        phone: that.mobile,
        code: c }).
      then(function (res) {
        uni.hideLoading();
        if (res.data.code == '0') {
          uni.showToast({
            title: '完成！',
            icon: 'success',
            duration: 2000,
            success: function success() {
              //完成后返回至安全设置
              if (getCurrentPages() >= 3) {
                uni.navigateBack({
                  delta: 3 });

              } else {
                uni.reLaunch({
                  url: 'security' });

              }

            } });

        }
      }).catch(function (err) {
        uni.hideLoading();
      });
    },


    //重新发送验证码
    resend: function resend() {
      var that = this;
      if (!that.mobile) {
        uni.showToast({
          icon: 'none',
          title: '手机号格式不正确！',
          duration: 2000 });

        return;
      }
      uni.showLoading({ mask: true });
      this.postStatus = true;
      this.$api.code.updatePhone({
        phone: that.mobile }).
      then(function (res) {
        uni.hideLoading();
        that.postStatus = false;
        if (res.data.code != '0') {
          uni.showToast({
            title: '获取验证码失败！',
            duration: 2000 });

        }
      }).catch(function (e) {
        that.postStatus = false;
        uni.hideLoading();
        that.timing = 0;
        console.log("catch", e);
      });
      that.timing = that.tNumber;
      var t = setInterval(function () {
        that.timing = that.timing - 1;
        if (that.timing <= 0) {
          clearTimeout(t);
        }
      }, 1000);
    } } };exports.default = _default;
/* WEBPACK VAR INJECTION */}.call(this, __webpack_require__(/*! ./node_modules/@dcloudio/uni-mp-toutiao/dist/index.js */ 1)["default"]))

/***/ }),

/***/ 402:
/*!*********************************************************************************************************************************************************!*\
  !*** F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue?vue&type=style&index=0&id=13b170d3&scoped=true&lang=css& ***!
  \*********************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_style_index_0_id_13b170d3_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! -!./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/css-loader/dist/cjs.js??ref--6-oneOf-1-1!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-2!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!./verification_phone.vue?vue&type=style&index=0&id=13b170d3&scoped=true&lang=css& */ 403);
/* harmony import */ var _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_style_index_0_id_13b170d3_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_style_index_0_id_13b170d3_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__);
/* harmony reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_style_index_0_id_13b170d3_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return _E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_style_index_0_id_13b170d3_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0__[key]; }) }(__WEBPACK_IMPORT_KEY__));
 /* harmony default export */ __webpack_exports__["default"] = (_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_mini_css_extract_plugin_dist_loader_js_ref_6_oneOf_1_0_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_css_loader_dist_cjs_js_ref_6_oneOf_1_1_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_loaders_stylePostLoader_js_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_webpack_preprocess_loader_index_js_ref_6_oneOf_1_2_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_postcss_loader_src_index_js_ref_6_oneOf_1_3_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_vue_cli_plugin_uni_packages_vue_loader_lib_index_js_vue_loader_options_E_HBuilderX_2_2_2_20190816_full_HBuilderX_plugins_uniapp_cli_node_modules_dcloudio_webpack_uni_mp_loader_lib_style_js_verification_phone_vue_vue_type_style_index_0_id_13b170d3_scoped_true_lang_css___WEBPACK_IMPORTED_MODULE_0___default.a); 

/***/ }),

/***/ 403:
/*!*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************!*\
  !*** ./node_modules/mini-css-extract-plugin/dist/loader.js??ref--6-oneOf-1-0!./node_modules/css-loader/dist/cjs.js??ref--6-oneOf-1-1!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib/loaders/stylePostLoader.js!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/webpack-preprocess-loader??ref--6-oneOf-1-2!./node_modules/postcss-loader/src??ref--6-oneOf-1-3!./node_modules/@dcloudio/vue-cli-plugin-uni/packages/vue-loader/lib??vue-loader-options!./node_modules/@dcloudio/webpack-uni-mp-loader/lib/style.js!F:/demo/reference/food/HX-SHOP 外卖商户模板/pages/user/account_security/verification_phone.vue?vue&type=style&index=0&id=13b170d3&scoped=true&lang=css& ***!
  \*************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

// extracted by mini-css-extract-plugin
    if(false) { var cssReload; }
  

/***/ })

},[[396,"common/runtime","common/vendor"]]]);
//# sourceMappingURL=data:application/json;charset=utf-8;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly8vRjovZGVtby9yZWZlcmVuY2UvZm9vZC9IWC1TSE9QIOWkluWNluWVhuaIt+aooeadvy9tYWluLmpzIiwid2VicGFjazovLy9GOi9kZW1vL3JlZmVyZW5jZS9mb29kL0hYLVNIT1Ag5aSW5Y2W5ZWG5oi35qih5p2/L3BhZ2VzL3VzZXIvYWNjb3VudF9zZWN1cml0eS92ZXJpZmljYXRpb25fcGhvbmUudnVlPzkzM2EiLCJ3ZWJwYWNrOi8vL0Y6L2RlbW8vcmVmZXJlbmNlL2Zvb2QvSFgtU0hPUCDlpJbljZbllYbmiLfmqKHmnb8vcGFnZXMvdXNlci9hY2NvdW50X3NlY3VyaXR5L3ZlcmlmaWNhdGlvbl9waG9uZS52dWU/NDk0YiIsIndlYnBhY2s6Ly8vRjovZGVtby9yZWZlcmVuY2UvZm9vZC9IWC1TSE9QIOWkluWNluWVhuaIt+aooeadvy9wYWdlcy91c2VyL2FjY291bnRfc2VjdXJpdHkvdmVyaWZpY2F0aW9uX3Bob25lLnZ1ZT83OWZhIiwid2VicGFjazovLy9GOi9kZW1vL3JlZmVyZW5jZS9mb29kL0hYLVNIT1Ag5aSW5Y2W5ZWG5oi35qih5p2/L3BhZ2VzL3VzZXIvYWNjb3VudF9zZWN1cml0eS92ZXJpZmljYXRpb25fcGhvbmUudnVlP2Y3NDYiLCJ3ZWJwYWNrOi8vL0Y6L2RlbW8vcmVmZXJlbmNlL2Zvb2QvSFgtU0hPUCDlpJbljZbllYbmiLfmqKHmnb8vcGFnZXMvdXNlci9hY2NvdW50X3NlY3VyaXR5L3ZlcmlmaWNhdGlvbl9waG9uZS52dWUiLCJ3ZWJwYWNrOi8vL0Y6L2RlbW8vcmVmZXJlbmNlL2Zvb2QvSFgtU0hPUCDlpJbljZbllYbmiLfmqKHmnb8vcGFnZXMvdXNlci9hY2NvdW50X3NlY3VyaXR5L3ZlcmlmaWNhdGlvbl9waG9uZS52dWU/MmRhMSIsIndlYnBhY2s6Ly8vRjovZGVtby9yZWZlcmVuY2UvZm9vZC9IWC1TSE9QIOWkluWNluWVhuaIt+aooeadvy9wYWdlcy91c2VyL2FjY291bnRfc2VjdXJpdHkvdmVyaWZpY2F0aW9uX3Bob25lLnZ1ZT83YjQ3Il0sIm5hbWVzIjpbImNyZWF0ZVBhZ2UiLCJQYWdlIiwid2F0Y2giLCJtYXhsZW5ndGgiLCJpbW1lZGlhdGUiLCJoYW5kbGVyIiwibmV3ViIsInJhbmdlcyIsImRhdGEiLCJjb2RlSW5kZXgiLCJtb2JpbGUiLCJjb2RlQXJyIiwiaXNQd2QiLCJ0eXBlIiwidGltaW5nIiwidE51bWJlciIsInBvc3RTdGF0dXMiLCJvbkxvYWQiLCJvcHRpb24iLCJyZWciLCJ0aGF0IiwiZXhlYyIsInVuaSIsInNob3dUb2FzdCIsImljb24iLCJ0aXRsZSIsImR1cmF0aW9uIiwicmVzZW5kIiwibWV0aG9kcyIsImdldFZhbCIsImUiLCJ2YWx1ZSIsImRldGFpbCIsImFyciIsInNwbGl0IiwibGVuZ3RoIiwiTnVtYmVyIiwiY29kZSIsImpvaW4iLCJjb25zb2xlIiwibG9nIiwidXBkYXRlUGhvbmUiLCJjIiwic2hvd0xvYWRpbmciLCJtYXNrIiwiJGFwaSIsInVzZXIiLCJwaG9uZSIsInRoZW4iLCJyZXMiLCJoaWRlTG9hZGluZyIsInN1Y2Nlc3MiLCJnZXRDdXJyZW50UGFnZXMiLCJuYXZpZ2F0ZUJhY2siLCJkZWx0YSIsInJlTGF1bmNoIiwidXJsIiwiY2F0Y2giLCJlcnIiLCJ0Iiwic2V0SW50ZXJ2YWwiLCJjbGVhclRpbWVvdXQiXSwibWFwcGluZ3MiOiI7Ozs7Ozs7Ozs7a0RBQUEsd0NBQW1CO0FBQ25CO0FBQ0EsdUk7QUFDQUEsVUFBVSxDQUFDQywyQkFBRCxDQUFWLEM7Ozs7Ozs7Ozs7Ozs7QUNIQTtBQUFBO0FBQUE7QUFBQTtBQUFBO0FBQUE7QUFBMkk7QUFDM0k7QUFDc0U7QUFDTDtBQUNxQzs7O0FBR3RHO0FBQ29NO0FBQ3BNLGdCQUFnQiw2TUFBVTtBQUMxQixFQUFFLHdGQUFNO0FBQ1IsRUFBRSx5R0FBTTtBQUNSLEVBQUUsa0hBQWU7QUFDakI7QUFDQTtBQUNBO0FBQ0E7QUFDQTtBQUNBLEVBQUUsNkdBQVU7QUFDWjtBQUNBOztBQUVBO0FBQ2UsZ0Y7Ozs7Ozs7Ozs7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R0FBYyxDQUE1QjtBQUNBLFlBQUdNLElBQUksQ0FBQ04sTUFBTCxJQUFlLENBQWxCLEVBQW9CO0FBQ25CK0Msc0JBQVksQ0FBQ0YsQ0FBRCxDQUFaO0FBQ0E7QUFDRCxPQUxrQixFQUtqQixJQUxpQixDQUFuQjtBQU1BLEtBdEZVLEVBN0NFLEU7Ozs7Ozs7Ozs7Ozs7QUMxRGY7QUFBQTtBQUFBO0FBQUE7QUFBK21DLENBQWdCLHltQ0FBRyxFQUFDLEM7Ozs7Ozs7Ozs7O0FDQW5vQztBQUNBLE9BQU8sS0FBVSxFQUFFLGtCQUtkIiwiZmlsZSI6InBhZ2VzL3VzZXIvYWNjb3VudF9zZWN1cml0eS92ZXJpZmljYXRpb25fcGhvbmUuanMiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgJ3VuaS1wYWdlcyc7aW1wb3J0ICdAZGNsb3VkaW8vdW5pLXN0YXQnO1xuaW1wb3J0IFZ1ZSBmcm9tICd2dWUnXG5pbXBvcnQgUGFnZSBmcm9tICcuL3BhZ2VzL3VzZXIvYWNjb3VudF9zZWN1cml0eS92ZXJpZmljYXRpb25fcGhvbmUudnVlJ1xuY3JlYXRlUGFnZShQYWdlKSIsImltcG9ydCB7IHJlbmRlciwgc3RhdGljUmVuZGVyRm5zLCByZWN5Y2xhYmxlUmVuZGVyLCBjb21wb25lbnRzIH0gZnJvbSBcIi4vdmVyaWZpY2F0aW9uX3Bob25lLnZ1ZT92dWUmdHlwZT10ZW1wbGF0ZSZpZD0xM2IxNzBkMyZzY29wZWQ9dHJ1ZSZcIlxudmFyIHJlbmRlcmpzXG5pbXBvcnQgc2NyaXB0IGZyb20gXCIuL3ZlcmlmaWNhdGlvbl9waG9uZS52dWU/dnVlJnR5cGU9c2NyaXB0Jmxhbmc9anMmXCJcbmV4cG9ydCAqIGZyb20gXCIuL3ZlcmlmaWNhdGlvbl9waG9uZS52dWU/dnVlJnR5cGU9c2NyaXB0Jmxhbmc9anMmXCJcbmltcG9ydCBzdHlsZTAgZnJvbSBcIi4vdmVyaWZpY2F0aW9uX3Bob25lLnZ1ZT92dWUmdHlwZT1zdHlsZSZpbmRleD0wJmlkPTEzYjE3MGQzJnNjb3BlZD10cnVlJmxhbmc9Y3NzJlwiXG5cblxuLyogbm9ybWFsaXplIGNvbXBvbmVudCAqL1xuaW1wb3J0IG5vcm1hbGl6ZXIgZnJvbSBcIiFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxAZGNsb3VkaW9cXFxcdnVlLWNsaS1wbHVnaW4tdW5pXFxcXHBhY2thZ2VzXFxcXHZ1ZS1sb2FkZXJcXFxcbGliXFxcXHJ1bnRpbWVcXFxcY29tcG9uZW50Tm9ybWFsaXplci5qc1wiXG52YXIgY29tcG9uZW50ID0gbm9ybWFsaXplcihcbiAgc2NyaXB0LFxuICByZW5kZXIsXG4gIHN0YXRpY1JlbmRlckZucyxcbiAgZmFsc2UsXG4gIG51bGwsXG4gIFwiMTNiMTcwZDNcIixcbiAgbnVsbCxcbiAgZmFsc2UsXG4gIGNvbXBvbmVudHMsXG4gIHJlbmRlcmpzXG4pXG5cbmNvbXBvbmVudC5vcHRpb25zLl9fZmlsZSA9IFwiRjovZGVtby9yZWZlcmVuY2UvZm9vZC9IWC1TSE9QIOWkluWNluWVhuaIt+aooeadvy9wYWdlcy91c2VyL2FjY291bnRfc2VjdXJpdHkvdmVyaWZpY2F0aW9uX3Bob25lLnZ1ZVwiXG5leHBvcnQgZGVmYXVsdCBjb21wb25lbnQuZXhwb3J0cyIsImV4cG9ydCAqIGZyb20gXCItIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx2dWUtY2xpLXBsdWdpbi11bmlcXFxccGFja2FnZXNcXFxcdnVlLWxvYWRlclxcXFxsaWJcXFxcbG9hZGVyc1xcXFx0ZW1wbGF0ZUxvYWRlci5qcz8/dnVlLWxvYWRlci1vcHRpb25zIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx2dWUtY2xpLXBsdWdpbi11bmlcXFxccGFja2FnZXNcXFxcd2VicGFjay1wcmVwcm9jZXNzLWxvYWRlclxcXFxpbmRleC5qcz8/cmVmLS0xNi0wIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx3ZWJwYWNrLXVuaS1tcC1sb2FkZXJcXFxcbGliXFxcXHRlbXBsYXRlLmpzIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx2dWUtY2xpLXBsdWdpbi11bmlcXFxccGFja2FnZXNcXFxcd2VicGFjay11bmktYXBwLWxvYWRlclxcXFxwYWdlLW1ldGEuanMhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcQGRjbG91ZGlvXFxcXHZ1ZS1jbGktcGx1Z2luLXVuaVxcXFxwYWNrYWdlc1xcXFx2dWUtbG9hZGVyXFxcXGxpYlxcXFxpbmRleC5qcz8/dnVlLWxvYWRlci1vcHRpb25zIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx3ZWJwYWNrLXVuaS1tcC1sb2FkZXJcXFxcbGliXFxcXHN0eWxlLmpzIS4vdmVyaWZpY2F0aW9uX3Bob25lLnZ1ZT92dWUmdHlwZT10ZW1wbGF0ZSZpZD0xM2IxNzBkMyZzY29wZWQ9dHJ1ZSZcIiIsInZhciBjb21wb25lbnRzID0ge1xuICBcImh4LW5hdmJhclwiOiBmdW5jdGlvbigpIHtcbiAgICByZXR1cm4gaW1wb3J0KFxuICAgICAgLyogd2VicGFja0NodW5rTmFtZTogXCJjb21wb25lbnRzL2h4LW5hdmJhci9oeC1uYXZiYXJcIiAqLyBcIkAvY29tcG9uZW50cy9oeC1uYXZiYXIvaHgtbmF2YmFyLnZ1ZVwiXG4gICAgKVxuICB9XG59XG52YXIgcmVuZGVyID0gZnVuY3Rpb24oKSB7XG4gIHZhciBfdm0gPSB0aGlzXG4gIHZhciBfaCA9IF92bS4kY3JlYXRlRWxlbWVudFxuICB2YXIgX2MgPSBfdm0uX3NlbGYuX2MgfHwgX2hcbn1cbnZhciByZWN5Y2xhYmxlUmVuZGVyID0gZmFsc2VcbnZhciBzdGF0aWNSZW5kZXJGbnMgPSBbXVxucmVuZGVyLl93aXRoU3RyaXBwZWQgPSB0cnVlXG5cbmV4cG9ydCB7IHJlbmRlciwgc3RhdGljUmVuZGVyRm5zLCByZWN5Y2xhYmxlUmVuZGVyLCBjb21wb25lbnRzIH0iLCJpbXBvcnQgbW9kIGZyb20gXCItIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXGJhYmVsLWxvYWRlclxcXFxsaWJcXFxcaW5kZXguanMhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcQGRjbG91ZGlvXFxcXHZ1ZS1jbGktcGx1Z2luLXVuaVxcXFxwYWNrYWdlc1xcXFx3ZWJwYWNrLXByZXByb2Nlc3MtbG9hZGVyXFxcXGluZGV4LmpzPz9yZWYtLTEyLTEhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcQGRjbG91ZGlvXFxcXHdlYnBhY2stdW5pLW1wLWxvYWRlclxcXFxsaWJcXFxcc2NyaXB0LmpzIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx2dWUtY2xpLXBsdWdpbi11bmlcXFxccGFja2FnZXNcXFxcdnVlLWxvYWRlclxcXFxsaWJcXFxcaW5kZXguanM/P3Z1ZS1sb2FkZXItb3B0aW9ucyFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxAZGNsb3VkaW9cXFxcd2VicGFjay11bmktbXAtbG9hZGVyXFxcXGxpYlxcXFxzdHlsZS5qcyEuL3ZlcmlmaWNhdGlvbl9waG9uZS52dWU/dnVlJnR5cGU9c2NyaXB0Jmxhbmc9anMmXCI7IGV4cG9ydCBkZWZhdWx0IG1vZDsgZXhwb3J0ICogZnJvbSBcIi0hRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcYmFiZWwtbG9hZGVyXFxcXGxpYlxcXFxpbmRleC5qcyFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxAZGNsb3VkaW9cXFxcdnVlLWNsaS1wbHVnaW4tdW5pXFxcXHBhY2thZ2VzXFxcXHdlYnBhY2stcHJlcHJvY2Vzcy1sb2FkZXJcXFxcaW5kZXguanM/P3JlZi0tMTItMSFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxAZGNsb3VkaW9cXFxcd2VicGFjay11bmktbXAtbG9hZGVyXFxcXGxpYlxcXFxzY3JpcHQuanMhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcQGRjbG91ZGlvXFxcXHZ1ZS1jbGktcGx1Z2luLXVuaVxcXFxwYWNrYWdlc1xcXFx2dWUtbG9hZGVyXFxcXGxpYlxcXFxpbmRleC5qcz8/dnVlLWxvYWRlci1vcHRpb25zIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx3ZWJwYWNrLXVuaS1tcC1sb2FkZXJcXFxcbGliXFxcXHN0eWxlLmpzIS4vdmVyaWZpY2F0aW9uX3Bob25lLnZ1ZT92dWUmdHlwZT1zY3JpcHQmbGFuZz1qcyZcIiIsIi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cbi8vXG4vL1xuLy9cblxuZXhwb3J0IGRlZmF1bHQge1xuXHRcbiAgICB3YXRjaDoge1xuICAgICAgICBtYXhsZW5ndGg6IHtcbiAgICAgICAgICAgIGltbWVkaWF0ZTogdHJ1ZSxcbiAgICAgICAgICAgIGhhbmRsZXI6IGZ1bmN0aW9uKG5ld1YpIHtcbiAgICAgICAgICAgICAgICBpZiAobmV3ViA9PT0gNikge1xuICAgICAgICAgICAgICAgICAgICB0aGlzLnJhbmdlcyA9IFsxLCAyLCAzLCA0LCA1LCA2XVxuICAgICAgICAgICAgICAgIH0gZWxzZSB7XG4gICAgICAgICAgICAgICAgICAgIHRoaXMucmFuZ2VzID0gWzEsIDIsIDMsIDRdXG4gICAgICAgICAgICAgICAgfVxuICAgICAgICAgICAgfVxuICAgICAgICB9XG4gICAgfSxcbiAgICBkYXRhKCkge1xuICAgICAgICByZXR1cm4ge1xuICAgICAgICAgICAgY29kZUluZGV4OiAxLFxuXHRcdFx0bW9iaWxlOiAnJyxcbiAgICAgICAgICAgIGNvZGVBcnI6IFtdLFxuICAgICAgICAgICAgcmFuZ2VzOiBbMSwgMiwgMywgNF0sXG5cdFx0XHRtYXhsZW5ndGg6NCxcblx0XHRcdGlzUHdkOiBmYWxzZSxcblx0XHRcdHR5cGU6IFwiYm90dG9tXCIsXG5cdFx0XHR0aW1pbmc6IDAsXG5cdFx0XHR0TnVtYmVyOiA5MCxcblx0XHRcdHBvc3RTdGF0dXM6IGZhbHNlXG4gICAgICAgIH1cbiAgICB9LFxuXHRvbkxvYWQob3B0aW9uKSB7XG5cdFx0bGV0IHJlZyA9L14xWzMtOV1cXGR7OX0kLztcblx0XHRsZXQgdGhhdCA9IHRoaXM7XG5cdFx0XG5cdFx0aWYoIXJlZy5leGVjKG9wdGlvbi5tb2JpbGUpKXtcblx0XHRcdHVuaS5zaG93VG9hc3Qoe1xuXHRcdFx0XHRpY29uOidub25lJyxcblx0XHRcdFx0dGl0bGU6ICfmiYvmnLrlj7fmoLzlvI/kuI3mraPnoa7vvIEnLFxuXHRcdFx0XHRkdXJhdGlvbjogMjAwMFxuXHRcdFx0fSlcblx0XHR9ZWxzZXtcblx0XHRcdHRoaXMubW9iaWxlID0gb3B0aW9uLm1vYmlsZTtcblx0XHRcdHRoYXQucmVzZW5kKCk7XG5cdFx0fVxuXHRcdFxuXHRcdFxuXHR9LFxuICAgIG1ldGhvZHM6IHtcbiAgICAgICAgZ2V0VmFsKGUpIHtcbiAgICAgICAgICAgIGxldCB7IHZhbHVlIH0gPSBlLmRldGFpbFxuICAgICAgICAgICAgbGV0IGFyciA9IHZhbHVlLnNwbGl0KCcnKVxuICAgICAgICAgICAgdGhpcy5jb2RlSW5kZXggPSBhcnIubGVuZ3RoICsgMVxuICAgICAgICAgICAgdGhpcy5jb2RlQXJyID0gYXJyXG4gICAgICAgICAgICBpZiAodGhpcy5jb2RlSW5kZXggPiBOdW1iZXIodGhpcy5tYXhsZW5ndGgpKSB7XG5cdFx0XHRcdC8v6aqM6K+B56CB6L6T5YWl5a6M5oiQ5Lul5ZCO55qE5pON5L2cXG5cdFx0XHRcdGxldCBjb2RlID10aGlzLmNvZGVBcnIuam9pbignJyk7XG4gICAgICAgICAgICAgICAgY29uc29sZS5sb2coY29kZSk7XG5cdFx0XHRcdHRoaXMudXBkYXRlUGhvbmUoY29kZSk7XG4gICAgICAgICAgICB9XG4gICAgICAgIH0sXG5cdFx0Ly/pqozor4HmiYvmnLrlj7dcblx0XHR1cGRhdGVQaG9uZShjKXtcblx0XHRcdGxldCB0aGF0ID0gdGhpcztcblx0XHRcdHVuaS5zaG93TG9hZGluZyh7bWFzazp0cnVlfSk7XG5cdFx0XHRcblx0XHRcdHRoaXMuJGFwaS51c2VyLnVwZGF0ZVBob25lKHtcblx0XHRcdFx0cGhvbmU6IHRoYXQubW9iaWxlLFxuXHRcdFx0XHRjb2RlOiBjXG5cdFx0XHR9KS50aGVuKChyZXMpPT57XG5cdFx0XHRcdHVuaS5oaWRlTG9hZGluZygpO1xuXHRcdFx0XHRpZihyZXMuZGF0YS5jb2RlID09ICcwJyl7XG5cdFx0XHRcdFx0dW5pLnNob3dUb2FzdCh7XG5cdFx0XHRcdFx0ICAgIHRpdGxlOiAn5a6M5oiQ77yBJyxcblx0XHRcdFx0XHRcdGljb246ICdzdWNjZXNzJyxcblx0XHRcdFx0XHQgICAgZHVyYXRpb246IDIwMDAsXG5cdFx0XHRcdFx0XHRzdWNjZXNzOmZ1bmN0aW9uKCl7XG5cdFx0XHRcdFx0XHRcdC8v5a6M5oiQ5ZCO6L+U5Zue6Iez5a6J5YWo6K6+572uXG5cdFx0XHRcdFx0XHRcdGlmKGdldEN1cnJlbnRQYWdlcygpID49IDMpe1xuXHRcdFx0XHRcdFx0XHRcdHVuaS5uYXZpZ2F0ZUJhY2soe1xuXHRcdFx0XHRcdFx0XHRcdCAgICBkZWx0YTogM1xuXHRcdFx0XHRcdFx0XHRcdH0pO1xuXHRcdFx0XHRcdFx0XHR9ZWxzZXtcblx0XHRcdFx0XHRcdFx0XHR1bmkucmVMYXVuY2goe1xuXHRcdFx0XHRcdFx0XHRcdCAgICB1cmw6ICdzZWN1cml0eSdcblx0XHRcdFx0XHRcdFx0XHR9KTtcblx0XHRcdFx0XHRcdFx0fVxuXHRcdFx0XHRcdFx0XHRcblx0XHRcdFx0XHRcdH1cblx0XHRcdFx0XHR9KTtcblx0XHRcdFx0fVxuXHRcdFx0fSkuY2F0Y2goKGVycik9Pntcblx0XHRcdFx0dW5pLmhpZGVMb2FkaW5nKCk7XG5cdFx0XHR9KVxuXHRcdH0sXG5cdFx0XG5cdFx0XG5cdFx0Ly/ph43mlrDlj5HpgIHpqozor4HnoIFcblx0XHRyZXNlbmQoKXtcblx0XHRcdGxldCB0aGF0ID0gdGhpcztcblx0XHRcdGlmKCF0aGF0Lm1vYmlsZSl7XG5cdFx0XHRcdHVuaS5zaG93VG9hc3Qoe1xuXHRcdFx0XHRcdGljb246J25vbmUnLFxuXHRcdFx0XHRcdHRpdGxlOiAn5omL5py65Y+35qC85byP5LiN5q2j56Gu77yBJyxcblx0XHRcdFx0XHRkdXJhdGlvbjogMjAwMFxuXHRcdFx0XHR9KVxuXHRcdFx0XHRyZXR1cm47XG5cdFx0XHR9XG5cdFx0XHR1bmkuc2hvd0xvYWRpbmcoe21hc2s6dHJ1ZX0pO1xuXHRcdFx0dGhpcy5wb3N0U3RhdHVzID0gdHJ1ZVxuXHRcdFx0dGhpcy4kYXBpLmNvZGUudXBkYXRlUGhvbmUoe1xuXHRcdFx0XHRwaG9uZTogdGhhdC5tb2JpbGVcblx0XHRcdH0pLnRoZW4oKHJlcyk9Pntcblx0XHRcdFx0dW5pLmhpZGVMb2FkaW5nKCk7XG5cdFx0XHRcdHRoYXQucG9zdFN0YXR1cyA9IGZhbHNlO1xuXHRcdFx0XHRpZihyZXMuZGF0YS5jb2RlICE9ICcwJyl7XG5cdFx0XHRcdFx0dW5pLnNob3dUb2FzdCh7XG5cdFx0XHRcdFx0ICAgIHRpdGxlOiAn6I635Y+W6aqM6K+B56CB5aSx6LSl77yBJyxcblx0XHRcdFx0XHQgICAgZHVyYXRpb246IDIwMDBcblx0XHRcdFx0XHR9KTtcblx0XHRcdFx0fVxuXHRcdFx0fSkuY2F0Y2goKGUpPT57IFxuXHRcdFx0XHR0aGF0LnBvc3RTdGF0dXMgPSBmYWxzZVxuXHRcdFx0XHR1bmkuaGlkZUxvYWRpbmcoKTtcblx0XHRcdFx0dGhhdC50aW1pbmcgPSAwXG5cdFx0XHRcdGNvbnNvbGUubG9nKFwiY2F0Y2hcIixlKTtcblx0XHRcdH0pXG5cdFx0XHR0aGF0LnRpbWluZyA9IHRoYXQudE51bWJlcjtcblx0XHRcdGxldCB0ID0gc2V0SW50ZXJ2YWwoZnVuY3Rpb24oKXtcblx0XHRcdFx0dGhhdC50aW1pbmcgPSB0aGF0LnRpbWluZyAtIDFcblx0XHRcdFx0aWYodGhhdC50aW1pbmcgPD0gMCl7XG5cdFx0XHRcdFx0Y2xlYXJUaW1lb3V0KHQpO1xuXHRcdFx0XHR9XG5cdFx0XHR9LDEwMDApXG5cdFx0fVxuXHRcbiAgICB9XG59XG4iLCJpbXBvcnQgbW9kIGZyb20gXCItIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXG1pbmktY3NzLWV4dHJhY3QtcGx1Z2luXFxcXGRpc3RcXFxcbG9hZGVyLmpzPz9yZWYtLTYtb25lT2YtMS0wIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXGNzcy1sb2FkZXJcXFxcZGlzdFxcXFxjanMuanM/P3JlZi0tNi1vbmVPZi0xLTEhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcQGRjbG91ZGlvXFxcXHZ1ZS1jbGktcGx1Z2luLXVuaVxcXFxwYWNrYWdlc1xcXFx2dWUtbG9hZGVyXFxcXGxpYlxcXFxsb2FkZXJzXFxcXHN0eWxlUG9zdExvYWRlci5qcyFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxAZGNsb3VkaW9cXFxcdnVlLWNsaS1wbHVnaW4tdW5pXFxcXHBhY2thZ2VzXFxcXHdlYnBhY2stcHJlcHJvY2Vzcy1sb2FkZXJcXFxcaW5kZXguanM/P3JlZi0tNi1vbmVPZi0xLTIhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxccG9zdGNzcy1sb2FkZXJcXFxcc3JjXFxcXGluZGV4LmpzPz9yZWYtLTYtb25lT2YtMS0zIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx2dWUtY2xpLXBsdWdpbi11bmlcXFxccGFja2FnZXNcXFxcdnVlLWxvYWRlclxcXFxsaWJcXFxcaW5kZXguanM/P3Z1ZS1sb2FkZXItb3B0aW9ucyFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxAZGNsb3VkaW9cXFxcd2VicGFjay11bmktbXAtbG9hZGVyXFxcXGxpYlxcXFxzdHlsZS5qcyEuL3ZlcmlmaWNhdGlvbl9waG9uZS52dWU/dnVlJnR5cGU9c3R5bGUmaW5kZXg9MCZpZD0xM2IxNzBkMyZzY29wZWQ9dHJ1ZSZsYW5nPWNzcyZcIjsgZXhwb3J0IGRlZmF1bHQgbW9kOyBleHBvcnQgKiBmcm9tIFwiLSFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxtaW5pLWNzcy1leHRyYWN0LXBsdWdpblxcXFxkaXN0XFxcXGxvYWRlci5qcz8/cmVmLS02LW9uZU9mLTEtMCFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxjc3MtbG9hZGVyXFxcXGRpc3RcXFxcY2pzLmpzPz9yZWYtLTYtb25lT2YtMS0xIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXEBkY2xvdWRpb1xcXFx2dWUtY2xpLXBsdWdpbi11bmlcXFxccGFja2FnZXNcXFxcdnVlLWxvYWRlclxcXFxsaWJcXFxcbG9hZGVyc1xcXFxzdHlsZVBvc3RMb2FkZXIuanMhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcQGRjbG91ZGlvXFxcXHZ1ZS1jbGktcGx1Z2luLXVuaVxcXFxwYWNrYWdlc1xcXFx3ZWJwYWNrLXByZXByb2Nlc3MtbG9hZGVyXFxcXGluZGV4LmpzPz9yZWYtLTYtb25lT2YtMS0yIUU6XFxcXEhCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsXFxcXEhCdWlsZGVyWFxcXFxwbHVnaW5zXFxcXHVuaWFwcC1jbGlcXFxcbm9kZV9tb2R1bGVzXFxcXHBvc3Rjc3MtbG9hZGVyXFxcXHNyY1xcXFxpbmRleC5qcz8/cmVmLS02LW9uZU9mLTEtMyFFOlxcXFxIQnVpbGRlclguMi4yLjIuMjAxOTA4MTYuZnVsbFxcXFxIQnVpbGRlclhcXFxccGx1Z2luc1xcXFx1bmlhcHAtY2xpXFxcXG5vZGVfbW9kdWxlc1xcXFxAZGNsb3VkaW9cXFxcdnVlLWNsaS1wbHVnaW4tdW5pXFxcXHBhY2thZ2VzXFxcXHZ1ZS1sb2FkZXJcXFxcbGliXFxcXGluZGV4LmpzPz92dWUtbG9hZGVyLW9wdGlvbnMhRTpcXFxcSEJ1aWxkZXJYLjIuMi4yLjIwMTkwODE2LmZ1bGxcXFxcSEJ1aWxkZXJYXFxcXHBsdWdpbnNcXFxcdW5pYXBwLWNsaVxcXFxub2RlX21vZHVsZXNcXFxcQGRjbG91ZGlvXFxcXHdlYnBhY2stdW5pLW1wLWxvYWRlclxcXFxsaWJcXFxcc3R5bGUuanMhLi92ZXJpZmljYXRpb25fcGhvbmUudnVlP3Z1ZSZ0eXBlPXN0eWxlJmluZGV4PTAmaWQ9MTNiMTcwZDMmc2NvcGVkPXRydWUmbGFuZz1jc3MmXCIiLCIvLyBleHRyYWN0ZWQgYnkgbWluaS1jc3MtZXh0cmFjdC1wbHVnaW5cbiAgICBpZihtb2R1bGUuaG90KSB7XG4gICAgICAvLyAxNTg3NzE5MTQzNTkwXG4gICAgICB2YXIgY3NzUmVsb2FkID0gcmVxdWlyZShcIkU6L0hCdWlsZGVyWC4yLjIuMi4yMDE5MDgxNi5mdWxsL0hCdWlsZGVyWC9wbHVnaW5zL3VuaWFwcC1jbGkvbm9kZV9tb2R1bGVzL21pbmktY3NzLWV4dHJhY3QtcGx1Z2luL2Rpc3QvaG1yL2hvdE1vZHVsZVJlcGxhY2VtZW50LmpzXCIpKG1vZHVsZS5pZCwge1wiaG1yXCI6dHJ1ZSxcInB1YmxpY1BhdGhcIjpcIi4uLy4uL1wiLFwibG9jYWxzXCI6ZmFsc2V9KTtcbiAgICAgIG1vZHVsZS5ob3QuZGlzcG9zZShjc3NSZWxvYWQpO1xuICAgICAgbW9kdWxlLmhvdC5hY2NlcHQodW5kZWZpbmVkLCBjc3NSZWxvYWQpO1xuICAgIH1cbiAgIl0sInNvdXJjZVJvb3QiOiIifQ==