package com.jhj.gulimall.product.controller;

import com.jhj.common.utils.PageUtils;
import com.jhj.common.utils.R;
import com.jhj.gulimall.product.entity.AttrEntity;
import com.jhj.gulimall.product.entity.AttrGroupEntity;
import com.jhj.gulimall.product.service.AttrAttrgroupRelationService;
import com.jhj.gulimall.product.service.AttrGroupService;
import com.jhj.gulimall.product.service.AttrService;
import com.jhj.gulimall.product.service.CategoryService;
import com.jhj.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * 属性分组
 *
 * @author jhj
 * @email jhj@gmail.com
 * @date 2022-05-14 15:10:18
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Resource
    private CategoryService categoryService;
    @Resource
    AttrService attrService;
    @Resource
    AttrAttrgroupRelationService attrAttrgroupRelationService;
    @PostMapping("/attr/relation")
    //@RequiresPermissions("product:attrgroup:list")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos) {
        attrAttrgroupRelationService.saveBatch(vos);
        return R.ok();
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteRelation(vos);
    return R.ok();
    }
    /**
     * 列表
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    //@RequiresPermissions("product:attrgroup:list")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> entities=attrService.getRelationAttr(attrgroupId);

        return R.ok().put("data", entities);
    }

    @GetMapping("/{attrgroupId}/noattr/relation")
    //@RequiresPermissions("product:attrgroup:list")
    public R attrNoRelation(@RequestParam Map<String, Object> params,@PathVariable("attrgroupId") Long attrgroupId) {
        PageUtils pageUtils =attrService.getNoRelationAttr(params,attrgroupId);

        return R.ok().put("page", pageUtils);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {
//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page=attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long[] path=categoryService.findCatelogPath(attrGroup.getCatelogId());
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

//    /**
//     * 删除
//     */
//    @PostMapping("/attr/relation/delete")
//    //@RequiresPermissions("product:attrgroup:delete")
//    public R delete(@RequestBody Long[] attrGroupIds) {
//        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
//
//        return R.ok();
//    }

}
