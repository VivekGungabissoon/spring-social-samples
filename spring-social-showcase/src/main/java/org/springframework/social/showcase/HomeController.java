/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.social.showcase;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;

import org.springframework.social.connect.ServiceProvider;
import org.springframework.social.showcase.account.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private final List<ServiceProvider<?>> serviceProviders;
	
	private final AccountRepository accountRepository;

	@Inject
	public HomeController(List<ServiceProvider<?>> serviceProviders, AccountRepository userRepository) {
		this.serviceProviders = serviceProviders;
		this.accountRepository = userRepository;
	}

	@RequestMapping("/")
	public String home(Principal currentUser, Model model) {
		for (ServiceProvider<?> serviceProvider : serviceProviders) {
			model.addAttribute(serviceProvider.getId() + "_status", serviceProvider.getConnections(currentUser.getName()).size() > 0 ? "Yes" : "No");
		}
		model.addAttribute(accountRepository.findAccountByUsername(currentUser.getName()));
		return "home";
	}
}