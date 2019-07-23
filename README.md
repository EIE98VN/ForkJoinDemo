# ForkJoinDemo
	 	 	
1. Thuật toán Fork/Join
- Khái niệm: Fork/Join là mô hình xử lý song song bằng cách tận dụng tất cả các lõi bộ xử lý có sẵn. Đây có thể coi như là 1 design pattern, và đã được đưa ra từ năm 1963. Trong đó:
+ Fork: đệ quy chia nhỏ nhiệm vụ thành các tác vụ nhỏ hơn cho đến khi chúng đơn giản đủ để xử lý bất đồng bộ. Bằng cách đó, mối tác vụ con có thể được thực hiện song song bởi các CPU khác nhau, hoặc các luồng khác nhau trên CPU
+ Join: các tác vụ nhỏ sau đó được gộp lại với nhau theo đệ quy khi đã thực hiện xong, trả lại kết quả cuối cùng.
- Pseudocode:
giải quyết (tác vụ):
	if(tác vụ đủ nhỏ)
		giải quyết tác vụ trực tiếp
	else:
		chia nhỏ tác vụ thành các tác vụ con
			giải quyết (tác vụ con)
		gộp các tác vụ con lại
		trả lại kết quả
Một ví dụ quen thuộc có sự tương đồng với thuật toán Fork/Join là Merge Sort (divide and conquer)
- ForkJoinPool tương tự như Java ExecutorService nhưng ForkJoinPool phân chia các tác vụ cho các luồng thực thi trong Thread Pool. 

- Lưu ý:
+ Thuật toán Fork/Join thực sự có ý nghĩa khi nhiệm vụ cần thực hiện là đủ lớn. Tức là có 1 khoản chi phí cho việc tách nhiệm vụ lớn thành các tác vụ nhỏ hơn, vì vậy với số lượng nhỏ, tốc độ thực thi công việc hoàn toàn có thể không được tăng thêm.
+ Không phải tất cả các tác vụ con đểu trả về một kết quả. Trong trường hợp như vậy, thì một nhiệm vụ chỉ cần đợi cho các công việc phụ của nó hoàn thành mà không có sự kết hợp nào diễn ra sau đó.
- Fork/Join trong Java:
+ ForkJoinPool là một Thread Pool đặc biệt được thiết kế để làm việc tốt với chia tách công việc fork/ join:
Framework Fork/ Join sử dụng thuật toán work-stealing. Các luồng sẽ thực thi công việc của mình trên một bộ xử lý riêng biệt (thread/ processor). 
Khi làm hết việc của mình, nó lấy bớt (steal) các tác vụ từ các luồng khác đang bận rộn.

ForkJoinTask<V>: một abstract class định nghĩa task sẽ được thực thi trong một ForkJoinPool.
 	
RecursiveAction: là một lớp con của ForkJoinTask, nó thực thi tác vụ mà không trả lại bất kỳ kết quả nào. Override lại phương thức compute().
 	
RecursiveTask<V>: là một lớp con của ForkJoinTask, nó thực thi tác vụ mà có trả lại kết quả (task). Override lại phương thức compute().
 	
Cú pháp tạo ForkJoinPool: 1 ForkJoinPool forkJoinPool = new ForkJoinPool(numOfProcessors); 	
 	
Tạo một ForkJoinPool với tham số là số lượng luồng hoặc các CPU bạn muốn làm việc đồng thời trên các nhiệm vụ được truyền vào ForkJoinPool. Nếu không xác định numOfProcessors, nó sẽ lấy số bộ vi xử lý có sẵn cho máy ảo Java để thực thi.



