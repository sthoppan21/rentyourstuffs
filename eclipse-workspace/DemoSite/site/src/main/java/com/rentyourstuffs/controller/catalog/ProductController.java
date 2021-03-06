/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rentyourstuffs.controller.catalog;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductAttribute;
import org.broadleafcommerce.core.web.controller.catalog.BroadleafProductController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.rentyourstuffs.customService.product.RYSProductAttributeService;
import com.rentyourstuffs.customService.product.RYSRentalTermService;
import com.rentyourstuffs.customentities.RYSRentalTerm;

/**
 * This class works in combination with the ProductHandlerMapping which finds a product based upon
 * the passed in URL.
 */
@Controller("blProductController")
public class ProductController extends BroadleafProductController {
    
	@Autowired 
	RYSRentalTermService rysRentalService;
	@Resource(name="rysProductAttributeService")
	RYSProductAttributeService rysProductAttributeService;
	
	
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
      ModelAndView model=   super.handleRequest(request, response);	
      Product product=(Product)model.getModel().get("product");
      List<RYSRentalTerm> rentTermForProduct=rysRentalService.getRentalTerms();
      List<ProductAttribute> productAttributes=rysProductAttributeService.getAllProductAttributesById(product.getId());
      model.addObject("rentTermForProduct",rentTermForProduct);
      model.addObject("dueRentalDates",rysRentalService.getDueDatesForRentalTerms(rentTermForProduct));
      model.addObject("productAttributes",productAttributes);
      return model;
        
    }

	

}
