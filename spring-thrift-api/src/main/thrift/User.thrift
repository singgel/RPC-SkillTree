namespace java com.hks.springthriftapi.service
service HelloWorldService {
  string sayHello(1:string username)
  string getRandom()
}