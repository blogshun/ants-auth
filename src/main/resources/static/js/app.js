/**
 * Created by Mr liu on 2017-08-26.
 */


var app = {
    config: {
        ctx: ""
    },
    init: function (defaultConfig) {
        // 初始态定义
        if (typeof (defaultConfig) != "undefined")
            this._config = $.extend(this.config, defaultConfig);

        //初始化菜单
        this._initMenus();

        //初始化顶部菜单点击
        this._topNavBar();

        //初始化全局Ajax
        this._initAjax();

        //初始化全局系统对话框以及提示
        this._initWindowTips();

        //初始化内容面板大小
        this._initContainerSize();

        return this;

    },
    _topNavBar: function () {
        var that = this;
        //上传管理员头像
        $("#_pic").change(function () {
            var actionUrl = that.config.ctx + "/user/uploadAvatar";
            $("#fileForm").ajaxSubmit({
                type: "POST",
                dataType: "json",
                url: actionUrl,
                data: {"action": "TemporaryImage"},
                success: function (res) {
                    if (res.code == 0) {
                        alert(res.data);
                        //$(".avatar img").attr("src", data.content);
                    } else
                        alert(res.message);
                },
                async: true
            });
        });


        //顶部管理员信息展开
        var adminSetup = function () {
            var hoverTimer, outTimer;
            $('#admin-manager-btn,.manager-menu,.admincp-map').mouseenter(function () {
                clearTimeout(outTimer);
                hoverTimer = setTimeout(function () {
                    $('.manager-menu').show();
                    $('#admin-manager-btn').removeClass().addClass("arrow-close");
                }, 200);
            });

            $('#admin-manager-btn,.manager-menu,.admincp-map').mouseleave(function () {
                clearTimeout(hoverTimer);
                outTimer = setTimeout(function () {
                    $('.manager-menu').hide();
                    $('#admin-manager-btn').removeClass().addClass("arrow");
                }, 100);
            });
        }
        adminSetup();

        //消息通知
        var message = function () {
            var hoverTimer, outTimer;
            $('.msg,#msg_Container').mouseenter(function () {
                clearTimeout(outTimer);
                hoverTimer = setTimeout(function () {
                    $('#msg_Container').show();
                }, 200);
            });

            $('.msg,#msg_Container').mouseleave(function () {
                clearTimeout(hoverTimer);
                outTimer = setTimeout(function () {
                    $('#msg_Container').hide();
                }, 100);
            });
        }
        message();
    },
    _initMenus: function () {
        var that = this;
        $("div.treeMenu>ul>li>div.title").click(function () {
            var target = $(this).attr("target");
            if (target) {
                var title = $(this).text();
                var type = $(this).attr("type");
                that.loadMain(target, type, title);
            } else {
                var parent = $(this).parent();
                parent.siblings().removeClass("current").end().addClass("current");
                var submenu = parent.find(".sub-menu");
                if (submenu.hasClass("on")) {
                    submenu.slideUp().removeClass("on");
                    if (submenu.length != 0) $(this).find("span").attr("class", "fa fa-angle-right");
                } else {
                    $(".sub-menu.on").slideUp().removeClass("on");
                    submenu.slideDown().addClass("on");
                    $(this).parents("ul").find("span.icon").attr("class", "fa fa-angle-right");
                    if (submenu.length != 0) $(this).find("span").attr("class", "fa fa-angle-down");
                }
            }
        });

        $("div.sub-menu>div").click(function () {
            var title = $(this).parents("li").find("div.title").text()+" - "+$(this).text();
            var target = $(this).attr("target");
            var type = $(this).attr("type");
            $(this).parents("ul").find(".active").removeClass("active");
            $(this).siblings().removeClass("active").end().addClass("active");
            that.loadMain(target, type, title);
        });

        that.loadMain(that.config.ctx + "/admin/main.jsp", 0, "主面板概览");

        //更换svg图标引用
        $("i.ac-font").each(function (i, obj) {
            var sizeCls = $(obj).attr("sizeCls");
            if (typeof (sizeCls) == "undefined")
                sizeCls = "icon";
            $(obj).html('<svg class="' + sizeCls + '" aria-hidden="true">'
                + '<use xlink:href="' + $(obj).attr("nameCls") + '"></use>'
                + '</svg>');
        });
    },
    loadMain: function (target, type, title, params) {
        var that = this;
        var t = new Date().getTime();
        $(".warpper>div.content").html('<div class="loading-box"><div class="loading"></div><img class="loading-logo" src="' + that.config.ctx + '/static/images/loading-logo.png" /></div>');
        if (typeof type == "undefined" || type == "0") {
            $.get(target, function (res) {
                delete window.setParams, window.init;
                $(".warpper>div.title").text(title);
                $(".warpper>div.content").html(res);
                if(window.init) window.init();
                if (typeof window.setParams != "undefined" && params) window.setParams(params);
            }, "html");
        } else if (type == "1") {
            $(".warpper>div.content").html('<iframe src="' + target + '" frameborder="0" scrolling="auto" style="height:' + that._body.bodyHeight + 'px;width:100%;">');
        } else if (type == "2") {
            alert(2);
        }
    },
    //获取url中的参数
    getUrlParam: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    },
    _initAjax: function () {
        $(document).ajaxSend(function (evt, request, settings) {// 请求开始

        }).ajaxComplete(function (event, request, settings, xhr) {
            if (settings.url.indexOf(".jsp") > 0) {

            } else {
                var res = JSON.parse(request.responseText);
                if (res.code == 1003) { //自定义权限异常
                    alert(settings.url + "\n" + res.ResponseMessage);
                    return;
                }
            }
        }).ajaxError(function (event, request, settings) {
            // 请求异常
            //服务停止, 返回 request readyState: 0, responseJSON: undefined, status: 0, statusText: "error"
        });
    },
    _initWindowTips: function () {

        //全局弹框
        window.alert = function (info) {
            mini.alert(info);
        };

        //全局对话框
        window.confirm = function (info, ok_fun, no_fun, ok_str, no_str) {
            if (!no_fun) no_fun = function () {
            };
            mini.confirm(info, "确定？",
                function (action) {
                    if (action == "ok") {
                        ok_fun();
                    } else {
                        no_fun();
                    }
                }
            );
        };

        //全局消息提示
        window['tips'] = {
            success: function (text) {
                mini.showTips({
                    content: "<i class='fa fa-check'></i>" + text,
                    state: 'success',      //default|success|info|warning|danger
                    x: 'center',          //left|center|right
                    y: 'top'          //top|center|bottom
                });
            },
            error: function (text) {
                mini.showTips({
                    content: "<i class='fa fa-close'></i>" + text,
                    state: 'danger',      //default|success|info|warning|danger
                    x: 'center',          //left|center|right
                    y: 'top',          //top|center|bottom
                    timeout: 3000
                });
            }
        }
    },
    _initContainerSize: function () {
        var that = this;
        that.windowChange();
        window.onresize = function () {
            that.windowChange();
        }
    },
    _body: {bodyWidth: 0, bodyHeight: 0},
    windowChange: function () {
        var bodyWidth = $("body").width();
        if (bodyWidth < 1204) {
            $("#workspace").height("92%");
        }

        var bodyHeight = $(document.body).height() - 50;

        $("div.treeMenu").css("height", bodyHeight + "px");
        $(".warpper>div.content").css("min-height", (bodyHeight - 100) + "px");
        this._body.bodyWidth = bodyWidth;
        this._body.bodyHeight = bodyHeight;
    },


    //自定义封装初始化表格增删改查 带条件查询等信息

    initTablePlugin: function (defaultConfig) {

        var config = {
            id: '',         //对象ID
            name: '',       //对象名称, 用于提示
            addCls: '',     //添加图标
            editCls: '',    //编辑图标
            addApi: '',     //添加接口
            findApi: '',    //查询接口
            removeApi: '',  //删除接口
            updateApi: '',  //修改接口
            params: {},     //加载参数
            showAddEvent: function (win, frm) {
            },                          //添加窗口显示事件
            showEditEvent: function (win, frm, res) {
            },                         //编辑窗口显示事件
            submitBeforeAddEvent: function (data, obj, win, frm) {
                return true
            },       //添加数据之前事件
            submitAddEvent: function (res, obj, win, frm) {
            },               //添加数据事件
            submitBeforeUpdateEvent: function (data, obj, win, frm) {
                return true
            },    //提交修改数据之前事件
            submitUpdateEvent: function (res, obj, win, frm) {
            },           //修改数据事件
            submitRemoveEvent: function (obj, win, frm) {
            },                //删除数据事件
            onCloseEvent: function (win, frm) {
            },                          //窗口关闭事件
            onSearchEvent: function () {
                return null
            }                       //搜索按钮事件
        }
        // 初始态定义
        if (typeof (defaultConfig) != "undefined")
            config = $.extend(config, defaultConfig);

        var grid = mini.get(config.id + "Grid");
        grid.load(config.params);

        if (config.winId != '') var win = mini.get(config.id + "Window");
        if (config.formId != '') var frm = new mini.Form(config.id + "Form");

        //打开添加窗口
        $("a#add").click(function () {
            frm.clear();
            win.setIconCls(config.addCls);
            win.setTitle("增加" + config.name);
            win.show();
            $("div.footer-btn").attr("data-option", 0);
            config.showAddEvent(win, frm);
        });

        //删除数据操作
        $("a#remove").click(function () {
            var rows = grid.getSelecteds();
            if (rows.length == 0)
                alert("请选着一条记录进行删除");
            else {
                confirm("您是否确定需要删除该条记录", function () {
                    var ids = [];
                    for (var i = 0, l = rows.length; i < l; i++) {
                        var r = rows[i];
                        ids.push(r.id);
                    }
                    $.post(config.removeApi, {ids: ids}, function (res) {
                        if (res.code == 0) {
                            tips.success("成功删除 " + res.data + " 条记录!");
                            grid.reload();
                            config.submitRemoveEvent(grid, win, frm);
                        } else
                            tips.error(res.message);
                    });
                });
            }
        });

        //打开编辑窗口进行数据查询操作
        $("a#edit").click(function () {
            frm.clear();
            var rows = grid.getSelecteds();
            if (rows.length == 0)
                alert("请选着一条记录进行编辑");
            else if (rows.length > 1)
                alert("只能选着一条记录进行编辑");
            else {
                $.get(config.findApi, {id: rows[0].id}, function (res) {
                    if (res.code == 0) {
                        win.setIconCls(config.editCls);
                        win.setTitle("编辑" + config.name);
                        config.showEditEvent(win, frm, res);
                        frm.setData(res.data);
                        win.show();
                        $("div.footer-btn").attr("data-option", 1);
                    } else
                        tips.error(res.message);
                });
            }
        });

        //确定提交增加或修改操作
        $("a#ok").click(function () {
            frm.validate();
            if (frm.isValid() == false) return;
            var data = frm.getData();
            var option = $("div.footer-btn").attr("data-option");
            if (option == "0") {
                var check = config.submitBeforeAddEvent(data, grid, win, frm);
                if (!check) return;
                $.post(config.addApi, data, function (res) {
                    if (res.code == 0) {
                        win.hide();
                        tips.success(config.name + "添加成功!");
                        grid.reload();
                        config.submitAddEvent(res, grid, win, frm);
                    } else
                        tips.error(res.message);
                });
            } else if (option == "1") {
                var check = config.submitBeforeUpdateEvent(data, grid, win, frm);
                if (!check) return;
                $.post(config.updateApi, data, function (res) {
                    if (res.code == 0) {
                        win.hide();
                        tips.success(config.name + "修改成功!");
                        grid.reload();
                        config.submitUpdateEvent(res, grid, win, frm);
                    } else
                        tips.error(res.message);
                });
            }
        });

        //关闭窗口
        $("a#cancel").click(function () {
            win.hide();
            config.onCloseEvent(win, frm);
        });

        //条件搜索
        $("a#search").click(function () {
            var filters = config.onSearchEvent();
            grid.load(filters);
        });
        return grid;
    }
}
