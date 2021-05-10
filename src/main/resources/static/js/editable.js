"use strict";
var params = null;
var columnsEdit = null;
var newColHtml = '<div class="btn-group pull-right">' +
    '<button id="btnEdit" type="button" class="btn btn-sm btn-default" onclick="btnEditClk(this);">' +
    '<span class="glyphicon glyphicon-pencil" > </span>' +
    '</button>' +
    '<button id="btnDelete" type="button" class="btn btn-sm btn-default" onclick="btnDeleteClk(this);">' +
    '<span class="glyphicon glyphicon-trash" > </span>' +
    '</button>' +
    '<button id="btnAccept" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="btnAcceptClk(this);">' +
    '<span class="glyphicon glyphicon-ok" > </span>' +
    '</button>' +
    '<button id="btnCancel" type="button" class="btn btn-sm btn-default" style="display:none;" onclick="btnCancelClk(this);">' +
    '<span class="glyphicon glyphicon-remove" > </span>' +
    '</button>' +
    '</div>';

var colEditHtml = '<td name="buttons">' + newColHtml + '</td>';

$.fn.setEditable = function (options) {
    var defaults = {
        columnsEd: null,
        afterEdit: function () {},
        beforeDelete: function () {},
        afterDelete: function () {},
        onAdd: function () {}
    };
    params = $.extend(defaults, options);
    var $thistab = this;
    $thistab.find('thead tr').append('<th name="buttons"></th>');
    $thistab.find('tbody tr').append(colEditHtml);
    if (params.columnsEd != null) {
        columnsEdit = params.columnsEd.split(',');
    }
};

function IterFieldEdit($cols, action) {
    var n = 0;
    $cols.each(function () {
        n++;
        if ($(this).attr('name') == 'buttons') return;
        if (!IsEditable(n - 1)) return;
        action($(this));
    });

    function IsEditable(idx) {
        if (columnsEdit == null) {
            return true;
        } else {
            for (var i = 0; i < columnsEdit.length; i++) {
                if (idx == columnsEdit[i]) return true;
            }
            return false;
        }
    }
}

function IsRowEditable($row) {
    if ($row.attr('id') == 'editing') {
        return true;
    } else {
        return false;
    }
}

function SetButtonsNormal(but) {
    $(but).parent().find('#btnAccept').hide();
    $(but).parent().find('#btnCancel').hide();
    $(but).parent().find('#btnEdit').show();
    $(but).parent().find('#btnDelete').show();
    var $row = $(but).parents('tr');
    $row.attr('id', '');
}

function SetButtonsEdit(but) {
    $(but).parent().find('#btnAccept').show();
    $(but).parent().find('#btnCancel').show();
    $(but).parent().find('#btnEdit').hide();
    $(but).parent().find('#btnDelete').hide();
    var $row = $(but).parents('tr');
    $row.attr('id', 'editing');
}

function btnAcceptClk(but) {
    var $row = $(but).parents('tr');
    var $cols = $row.find('td');
    if (!IsRowEditable($row)) return;
    IterFieldEdit($cols, function ($td) {
        var cont = $td.find('input').val();
        $td.html(cont);
    });
    SetButtonsNormal(but);
    params.afterEdit($row);
}

function btnCancelClk(but) {
    var $row = $(but).parents('tr');
    var $cols = $row.find('td');
    if (!IsRowEditable($row)) return;
    IterFieldEdit($cols, function ($td) {
        var cont = $td.find('div').html();
        $td.html(cont);
    });
    SetButtonsNormal(but);
}

function btnEditClk(but) {
    var $row = $(but).parents('tr');
    var $cols = $row.find('td');
    if (IsRowEditable($row)) return;
    var focused = false;
    IterFieldEdit($cols, function ($td) {
        var cont = $td.html();
        var div = '<div style="display: none;">' + cont + '</div>';
        var input = '<input class="form-control input-sm"  value="' + cont + '">';
        $td.html(div + input);
        if (!focused) {
            $td.find('input').focus();
            focused = true;
        }
    });
    SetButtonsEdit(but);
}

function btnDeleteClk(but) {
    var $row = $(but).parents('tr');
    params.beforeDelete($row);
    $row.remove();
    params.afterDelete();
}

function TableToJson(tabId) {
    var json = '{';
    var otArr = [];
    var tbl2 = $('#' + tabId + ' tr').each(function (i) {
        var x = $(this).children();
        var itArr = [];
        x.each(function () {
            itArr.push('"' + $(this).text() + '"');
        });
        otArr.push('"' + i + '": [' + itArr.join(',') + ']');
    })
    json += otArr.join(",") + '}'
    return json;
}


