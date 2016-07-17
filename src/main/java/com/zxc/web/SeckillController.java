package com.zxc.web;

import com.zxc.dto.Exposer;
import com.zxc.dto.SeckillExecution;
import com.zxc.dto.SeckillResult;
import com.zxc.entity.Seckill;
import com.zxc.enums.SeckillStatNum;
import com.zxc.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by zxc on 16/7/13.
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") long seckillId, Model model){
        Seckill seckill = seckillService.getSeckillById(seckillId);
        if(seckill == null){
            return "forward:list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";
    }

    //ajax方式获取秒杀接口
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST)
    public SeckillResult<Exposer> exposer(@PathVariable("seckillId") long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        }catch (Exception e){
            logger.error(e.getMessage());
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    //ajax方式执行秒杀
    @ResponseBody
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
            method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue("killphone") Long phone){
        if(phone == null){
            return new SeckillResult<SeckillExecution>(false, "用户未登录");
        }
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
            //秒杀成功
            if(seckillExecution.getState() == 1) return new SeckillResult<SeckillExecution>(true, seckillExecution);
            return new SeckillResult<SeckillExecution>(false, seckillExecution);
        }catch (Exception e){
            e.printStackTrace();
            logger.error(e.getMessage());
            return new SeckillResult<SeckillExecution>(false, new SeckillExecution(seckillId, SeckillStatNum.INNER_ERROR));
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date date = new Date();
        return new SeckillResult<Long>(true, date.getTime());
    }
}
