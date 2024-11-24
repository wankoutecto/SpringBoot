package com.example.account.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.account.model.Account;
import com.example.account.repository.AccountRepository;

@RestController
public class AccountController {
	private final AccountRepository repo;
	
	public AccountController(AccountRepository repo) {
		this.repo = repo;
	}
	
	@PostMapping("/account")
	public Account addAccount(@RequestBody Account account) {
		return repo.save(account);
	}
	
	@GetMapping("/account/{name}")
	public ResponseEntity<Account> getAccount(@PathVariable String name) {
		Account account = repo.findByName(name);
		if(account == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(account);
	}
	
	@GetMapping("/account")
	public List<Account>getAllAccount() {	
		return repo.findAll();
	}
	@DeleteMapping("/account/{name}")
	public void deleteAccount( @PathVariable String name) {
		repo.deleteByName(name);
	}
	@PutMapping("/account/{id}")
	public void updateAccount( @PathVariable Long id, @RequestBody Account account) {
		
			repo.save(account);
			
	}
}
