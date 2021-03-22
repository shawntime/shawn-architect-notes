#include<stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>						
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>	

void server() {
	
	// 创建socket连接
	int lfd = socket(AF_INET,SOCK_STREAM,0);
	struct sockaddr_in my_addr; 
	bzero(&my_addr, sizeof(my_addr));
	my_addr.sin_family = AF_INET; // ipv4
	my_addr.sin_port   = htons(9090);
	my_addr.sin_addr.s_addr = htonl(INADDR_ANY); 
	// 绑定端口
	bind(lfd, (struct sockaddr*)&my_addr, sizeof(my_addr));
	// 监听连接请求
	listen(lfd, 128);
	printf("listen client @port=%d...\n",9090);
	int lastfd = lfd;
	// 定义文件描述符集
	fd_set read_fd_set, all_fd_set;
	// 服务socket描述符加入set集合中
	FD_ZERO(&all_fd_set);
	FD_SET(lfd, &all_fd_set);
	printf("准备进入while循环\n");
	while (1) {
		read_fd_set = all_fd_set;
		printf("阻塞中... lastfd=%d\n", lastfd);
		int nready = select(lastfd+1, &read_fd_set, NULL, NULL, NULL);
		switch (nready) {
			case 0 :
				printf("select time out ......\n");
				break;
			case -1 :
				perror("select error \n");
				break;
			default:
				// 监听到新的客户端连接
				if (FD_ISSET(lfd, &read_fd_set)) {
					struct sockaddr_in client_addr;	
					socklen_t cliaddr_len = sizeof(client_addr);
					char cli_ip[INET_ADDRSTRLEN] = "";	
					// 肯定有连接不会阻塞
					int clientfd = accept(lfd, (struct sockaddr*)&client_addr, &cliaddr_len);
					inet_ntop(AF_INET, &client_addr.sin_addr, cli_ip, INET_ADDRSTRLEN);
					printf("----------------------------------------------\n");
					printf("client ip=%s,port=%d\n", cli_ip, ntohs(client_addr.sin_port));
					// 将clientfd加入读集合
					FD_SET(clientfd, &all_fd_set);	
					lastfd = clientfd;
					if(0 == --nready) {
						continue;
					}
				}
				int i;
				for (i = lfd + 1;i <= lastfd; i++) {
					// 处理读事件
					if (FD_ISSET(i, &read_fd_set)) {
						char recv_buf[512] = "";
						int rs = read(i, recv_buf, sizeof(recv_buf));
						if (rs == 0 ) {
							close(i);
							FD_CLR(i, &all_fd_set);
						} else {
							printf("%s\n",recv_buf);
							// 给每一个服务端写数据
							int j;
							for (j = lfd + 1;j <= lastfd; j++) {
								if (j != i) {
									write(j, recv_buf, strlen(recv_buf));
								}
							}
						}
					}
				}
		}
		
	}
}

int main(){
	server();
	return 0;
}
