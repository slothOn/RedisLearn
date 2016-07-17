/**
 * Created by zxc on 16/7/15.
 */
//模块化
var seckill = {
    URL : {
        now : function(){
            return '/seckill/time/now';
        },
        exposer : function(seckillId){
            return '/seckill/' + seckillId +'/exposer';
        },
        execution : function(seckillId, md5){
            return '/seckill/' + seckillId + "/" + md5 + "/execution";
        }
    },
    handleSeckill : function(seckillId, node){
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>').show();
        $.post(seckill.URL.exposer(seckillId), function(data){
            var exposer = data['data'];
            if(exposer['exposed']){
                var md5 = exposer['md5'];
                var killUrl = seckill.URL.execution(seckillId, md5);
                $("#killBtn").one('click', function(){
                    $("#killBtn").addClass("disabled");
                    $.post(killUrl, function(data){
                        if(data['success']){
                            var secdata = data['data'];
                            var state = secdata['state'];
                            var stateinfo = secdata['stateInfo'];
                            node.html("<label class='label label-success'>" + stateinfo + "</label>");
                        }else{
                            var secdata = data['data'];
                            var state = secdata['state'];
                            var stateinfo = secdata['stateInfo'];
                            node.html("<label class='label label-warning'>" + stateinfo + "</label>");
                        }
                    });
                });
            }else{
                seckill.countdown(seckillId, exposer['start'], exposer['end'], exposer['now']);
            }
        });
    },
    validatePhone : function(phone){
        return (phone && phone.length == 11 && !isNaN(phone));
    },
    countdown : function(seckillId, startTime, endTime, nowTime){
        var seckillbox = $("#seckill-box");
        if(nowTime > endTime){
            seckillbox.html("秒杀已经结束");
        }else if(nowTime < startTime){
            //进入倒计时
            var killTime = new Date(startTime + 1000);
            seckillbox.countdown(killTime, function(event){
               var format = event.strftime('秒杀时间倒计时: %D天 %H时 %M分 %S秒');
                seckillbox.html(format);
            }).on('finish .countdown', function(){
                seckill.handleSeckill(seckillId, seckillbox);
            });
        }else{
            //秒杀开始
            seckill.handleSeckill(seckillId, seckillbox);
        }
    },
    detail : {
        init : function(params){
            var keyPhone = $.cookie("killphone");

            if(!seckill.validatePhone(keyPhone)){
                var killPhoneModal = $("#killphoneModal");
                killPhoneModal.modal({
                    show:true,
                    keyboard:false,//禁止键盘事件
                    backdrop:'static'//禁止位置关闭
                });
                $("#killphoneBtn").click(function(){
                    var newphone = $("#killphoneKey").val();
                    if(seckill.validatePhone(newphone)){
                        $.cookie('killphone', newphone, {expires:7, path:"/seckill"});
                        window.location.reload();
                    }else{
                        $("#killphoneMsg").hide().html('<label class="label label-danger">手机号错误</label>').show(300);
                    }
                });
            }
            //已经登录
            var seckillId = params['seckillId'];
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            $.get(seckill.URL.now(), function(data){
                if(data && data['success']){
                    var nowTime = data['data'];
                    seckill.countdown(seckillId, startTime, endTime, nowTime);
                }else {
                    console.log(data['message']);
                }
            });
        }
    }
}