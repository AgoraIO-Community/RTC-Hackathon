<?php
/**
 * 小白接口的PHP代理
 *
 * 此文件，可用于为网站或者H5的混合应用，提供服务端代理，从而解决AJAX接口跨域、签名等问题。
 *
 * @link    http://www.okayapi.com
 * @author  dogstar 2017-12-28
 */

/**
// // 允许跨域 看情况开放，一般是不需要的
//     header('Content-Type: text/html;charset=utf-8');
//     header('Access-Control-Allow-Origin:*'); // *代表允许任何网址请求
//     header('Access-Control-Allow-Methods:POST,GET,OPTIONS,DELETE'); // 允许请求的类型
//     header('Access-Control-Allow-Credentials: true'); // 设置是否允许发送 cookies
//     header('Access-Control-Allow-Headers: Content-Type,Content-Length,Accept-Encoding,X-Requested-with, Origin'); // 设置允许自定义请求头的字段
// **/

// 如果需要查看你的接口域名、app_key和app_secrect，可访问：http://open.yesapi.cn/?r=App/Mine

define('OKAYAPI_HOST', '');        // TODO: 请在此处输入你的接口域名
define('OKAYAPI_APP_KEY', '');        // TODO: 请在此处输入你的app_key
define('OKAYAPI_APP_SECRECT', '');    // TODO: 请在此处输入你的app_secrect

$params = $_REQUEST;

session_start();
// 不再自动加上会话凭证，会导致通用数据自动与用户绑定。改为由客户端有需要时手动透传。
/**
if (isset($_SESSION['uuid'])) {
	$params['uuid'] = $_SESSION['uuid'];
}
if (isset($_SESSION['token'])) {
	$params['token'] = $_SESSION['token'];
}
*/

// 签名
$params['app_key'] = OKAYAPI_APP_KEY;
$params['sign'] = encryptAppKey($params, OKAYAPI_APP_SECRECT);

// 请求小白接口
$ch = curl_init();

curl_setopt($ch, CURLOPT_URL, OKAYAPI_HOST);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HEADER, 0);
curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);

curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, $params);

$rs = curl_exec($ch);
curl_close($ch);

// 纪录用户的登录凭证
if (isset($params['s']) && $params['s'] == 'App.User.Login') {
	$rsArr = json_decode($rs, true);
	if (isset($rsArr['data']['uuid']) && $rsArr['data']['token']) {
		$_SESSION['uuid'] = $rsArr['data']['uuid'];
		$_SESSION['token'] = $rsArr['data']['token'];
	}
}
// 输出接口结果
header('Content-type: application/json;charset=utf-8');
//echo mb_convert_encoding($rs,"UTF-8");
echo $rs;

function encryptAppKey($params, $appSecrect) {
    ksort($params);

    $paramsStrExceptSign = '';
    foreach ($params as $val) {
        $paramsStrExceptSign .= $val;
    }

    return strtoupper(md5($paramsStrExceptSign . $appSecrect));
}