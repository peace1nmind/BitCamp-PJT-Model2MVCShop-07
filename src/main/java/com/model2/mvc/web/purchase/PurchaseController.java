package com.model2.mvc.web.purchase;
// W 24... 

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Paging;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.TranCodeMapper;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {

	// Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	
	// Constructor
	public PurchaseController() {
		System.out.println(":: " + getClass().getSimpleName() + " default Constructor call\n");
	}

	
	// Method
	@RequestMapping("/listPurchase")
	public ModelAndView listPurchase(@RequestParam(value="page", required = false, defaultValue = "1") int page,
									 @RequestParam(value="historyPage", required = false, defaultValue = "1") int historyPage,
									 @SessionAttribute("user") User buyer,
									 HttpSession session) {
		
		System.out.println("/listPurchase");
		
		ModelAndView modelAndView = new ModelAndView();
		// Model 에 실을 map
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		/* Interceptor로 변경 필요 */
		if (buyer == null) {
			modelAndView.setViewName("redirect:/user/login");
		}
		
		
		/* 구매이력에 관한 로직 */
		Search search = new Search(page, pageSize);
		Map<String, Object> map = purchaseService.getPurchaseList(search, buyer.getUserId());
		Paging paging = new Paging((int) map.get("count"), search.getCurrentPage(), pageSize, pageUnit);
		
		modelMap.put("map", map);
		modelMap.put("paging", paging);
		modelMap.put("tranCodeMap", TranCodeMapper.getInstance().getMap());
		
		
		/* listPurchaseHistory 로직 */
		Search historySearch =  new Search(historyPage, pageSize);
		Map<String, Object> historyMap = purchaseService.getPurchaseHistoryList(historySearch, buyer.getUserId());
		Paging historyPaging = new Paging((int) historyMap.get("count"), historySearch.getCurrentPage(), pageSize, pageUnit);
		
		modelMap.put("historyMap", historyMap);
		modelMap.put("historyPaging", historyPaging);
		
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		modelAndView.addAllObjects(modelMap);
		
		return modelAndView;
	}

}
// class end