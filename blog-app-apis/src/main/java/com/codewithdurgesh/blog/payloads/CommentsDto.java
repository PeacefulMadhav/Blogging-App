package com.codewithdurgesh.blog.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsDto {
	
	private Integer id;
	
	private String content;

}
