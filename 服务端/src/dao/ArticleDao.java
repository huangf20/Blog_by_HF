package dao;

import java.util.List;

import bean.Article;
import bean.User;

public interface ArticleDao {
	void insert();
	void delete();
	void updata();
	List<Article> select(Article a);
	
	List<Article> search(Article a);
	List<User> checkHaveOrNot(User u);
	
	boolean addArticle(Article a);
	boolean deleteArticle(int id); 
	int maxId();
	

}
