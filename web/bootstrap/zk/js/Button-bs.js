/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global zul, zk */

zk.afterLoad('zul.wgt', function () {
    var _button = {};
 
zk.override(zul.wgt.Button.prototype, _button, {
    _sclass: 'btn-default',
    getZclass: function () {
        return 'btn';
    }
});
 
});

