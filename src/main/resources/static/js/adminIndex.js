$(function () {

    //顶部布局换色设置
    var bgColorSelectorColors = [{c: '#2B3442', cName: ''}, {c: '#981767', cName: ''}, {
        c: '#AD116B',
        cName: ''
    }, {c: '#B61944', cName: ''}, {c: '#AA1815', cName: ''}, {c: '#C4182D', cName: ''}, {
        c: '#D74641',
        cName: ''
    }, {c: '#ED6E4D', cName: ''}, {c: '#D78A67', cName: ''}, {c: '#F5A675', cName: ''}, {
        c: '#F8C888',
        cName: ''
    }, {c: '#F9D39B', cName: ''}, {c: '#F8DB87', cName: ''}, {c: '#FFD839', cName: ''}, {
        c: '#F9D12C',
        cName: ''
    }, {c: '#FABB3D', cName: ''}, {c: '#F8CB3C', cName: ''}, {c: '#F4E47E', cName: ''}, {
        c: '#F4ED87',
        cName: ''
    }, {c: '#DFE05E', cName: ''}, {c: '#CDCA5B', cName: ''}, {c: '#A8C03D', cName: ''}, {
        c: '#73A833',
        cName: ''
    }, {c: '#468E33', cName: ''}, {c: '#5CB147', cName: ''}, {c: '#6BB979', cName: ''}, {
        c: '#8EC89C',
        cName: ''
    }, {c: '#9AD0B9', cName: ''}, {c: '#97D3E3', cName: ''}, {c: '#7CCCEE', cName: ''}, {
        c: '#5AC3EC',
        cName: ''
    }, {c: '#16B8D8', cName: ''}, {c: '#49B4D6', cName: ''}, {c: '#6DB4E4', cName: ''}, {
        c: '#8DC2EA',
        cName: ''
    }, {c: '#BDB8DC', cName: ''}, {c: '#8381BD', cName: ''}, {c: '#7B6FB0', cName: ''}, {
        c: '#AA86BC',
        cName: ''
    }, {c: '#AA7AB3', cName: ''}, {c: '#935EA2', cName: ''}, {c: '#9D559C', cName: ''}, {
        c: '#C95C9D',
        cName: ''
    }, {c: '#DC75AB', cName: ''}, {c: '#EE7DAE', cName: ''}, {c: '#E6A5CA', cName: ''}, {
        c: '#EA94BE',
        cName: ''
    }, {c: '#D63F7D', cName: ''}, {c: '#C1374A', cName: ''}, {c: '#AB3255', cName: ''}, {
        c: '#A51263',
        cName: ''
    }, {c: '#7F285D', cName: ''}];
    var isShow = true;
    $("#trace_show").click(function () {
        if (isShow) {
            $("div.mainContext").animate({"margin-top": "86px"});
            $("div.treeMenu").animate({"top": "86px"});
            isShow = false;
        } else {
            $("div.mainContext").animate({"margin-top": "50px"});
            $("div.treeMenu").animate({"top": "50px"});
            isShow = true;
        }
        $("div.bgSelector").toggle(300, function () {
            if ($(this).html() == '') {
                $(this).sColor({
                    colors: bgColorSelectorColors,  // 必填，所有颜色 c:色号（必填） cName:颜色名称（可空）
                    colorsWidth: '50px',  // 必填，颜色的高度
                    colorsHeight: '31px',  // 必填，颜色的高度
                    curTop: '0', // 可选，颜色选择对象高偏移，默认0
                    curImg: ctx + '/static/images/cur.png',  //必填，颜色选择对象图片路径
                    form: 'click', // 可选，切换方式，drag或click，默认drag
                    keyEvent: true,  // 可选，开启键盘控制，默认true
                    prevColor: true, // 可选，开启切换页面后背景色是上一页面所选背景色，如不填则换页后背景色是defaultItem，默认false
                    defaultItem: ($.cookie('bgColorSelectorPosition') != null) ? $.cookie('bgColorSelectorPosition') : 22  // 可选，第几个颜色的索引作为初始颜色，默认第1个颜色
                });
            }
        });//切换显示
    });
    if ($.cookie('bgColorSelectorPosition') != null) {
        $("div.admin-header").css('background-color', bgColorSelectorColors[$.cookie('bgColorSelectorPosition')].c);
        $("div.treeMenu").css('background-color', bgColorSelectorColors[$.cookie('bgColorSelectorPosition')].c);
        //$('body').css('background-color', bgColorSelectorColors[$.cookie('bgColorSelectorPosition')].c);
    } else {
        $("div.admin-header").css('background-color', bgColorSelectorColors[0].c);
        $("div.treeMenu").css('background-color', bgColorSelectorColors[0].c);
        //$('body').css('background-color', bgColorSelectorColors[0].c);
    }

    $(window).resize(function () {
        $("div.treeMenu").css("height", ($(document.body).height() - 50) + "px");
    });

    $("div.treeMenu").css("height", ($(document.body).height() - 50) + "px");
});