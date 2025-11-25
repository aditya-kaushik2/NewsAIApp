package com.incapp.service;

import java.time.Instant;
import java.util.List;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.incapp.entity.NewsCheck;
import com.incapp.entity.UserAccount;
import com.incapp.repo.NewsRepo;
import com.incapp.repo.UserRepo;

@Service
public class NewsService {
//	@Autowired
//    private OpenAiChatModel chatModel;
	@Autowired
	private NewsRepo newsRepo;
	@Autowired
	UserRepo userRepo;
	
	public NewsCheck analyze(String articleText, String email) {
		UserAccount user=userRepo.findById(email).orElse(null);
		
        String summaryPrompt = "Summarize the following news into exactly 3 concise sentences:\n\n" + articleText;
        String credibilityPrompt = "Classify the credibility of the following news as one of: Credible, Suspicious, Fake. "
                + "Use indicators like source, specificity, sensationalism, verifiability. "
                + "Respond with just one word: Credible or Suspicious or Fake.\nNews:\n" + articleText;

        Client client = Client.builder().apiKey("Your-API-KEY").build();

        String summary = client.models.generateContent("gemini-2.5-flash",summaryPrompt,null).text();
        String credibility = client.models.generateContent("gemini-2.5-flash",credibilityPrompt,null).text();
        
        System.out.println(credibility);
        if (credibility != null) credibility = credibility.trim().split("\\s+")[0];

        NewsCheck n = NewsCheck.builder()
                .articleText(articleText)
                .summary(summary)
                .credibility(credibility)
                .createdAt(Instant.now())
                .user(user)
                .build();
        return newsRepo.save(n);
    }

	public List<NewsCheck> findAll() {
		return newsRepo.findAll();
	}

	public List<NewsCheck> findByUserOrderByCreatedAtDesc(String email) {
		UserAccount user=userRepo.findById(email).orElse(null);
		return newsRepo.findByUserOrderByCreatedAtDesc(user);
	}

	public NewsCheck getNewsById(long id) {
		return newsRepo.findById(id).orElse(null);
	}

	public void deleteNews(long id) {
		newsRepo.deleteById(id);
	}
}
