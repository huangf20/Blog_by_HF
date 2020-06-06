package test;

import Impl.ArticleImpl;
import bean.Article;

public class test {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		
		ArticleImpl at=new ArticleImpl();
		Article a=new Article();
		System.out.println(at.deleteArticle(10));
		at.close();

	}

}
