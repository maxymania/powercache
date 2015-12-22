Powercache
==========

An extended Caching solution for Java.

While it uses Googles Guava library (version 19) by default, Powercache
offers the capability to utilize external caches such as memcached or jcache.

CachingApplier and Object Proxy
-------------------------------

Unlike other cache solutions, Powercache wraps functions (more precisely, methods).
All you have to do is, write your Data retrieval code into a DAO-like
(DAO = Data Access Object) class. Make sure that you use an interface for your DAO.

```java
import io.github.maxymania.powercache.Cached;
public interface ICalc {
	@Cached("ICalc.calculate") // this must be an unique name for each method
	int calculate(int a,int b);
}

public class CCalc implements ICalc {
	@Override
	public int calculate(int a, int b) {
		System.out.println("calculate ("+a+","+b+")");
		return a+b;
	}
}
```

Then, the usage of the cache is straightforward.

```java
import io.github.maxymania.powercache.CachingApplier;
// create the DAO object...
ICalc calc = new CCalc();

// create the cache Applier
CachingApplier amp = new CachingApplier();

// wrap your object into a caching Proxy
calc = amp.apply(calc, ICalc.class);

// and use the methods as regular
System.out.println("call calculate ("+a+","+b+")");
int result = calc.calculate(1,2);
System.out.println("result = "+result);

// and again!
System.out.println("call calculate ("+a+","+b+")");
int result = calc.calculate(1,2);
System.out.println("result = "+result);

```

JCache Backend
--------------

In order to utilize external caches, it is sometimes necessary to use JCache.

```java
import io.github.maxymania.powercache.CachingApplier;
import io.github.maxymania.powercache.jcache.JCachingApplier;

// ...

// create the cache Applier
CachingApplier amp = new JCachingApplier("cacheName");
```

If JCache is used, you may need the following dependency, if not provided.

```xml
<dependency>
    <groupId>javax.cache</groupId>
    <artifactId>cache-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

Memcached Backend
-----------------

In some installations, you may want to use Memcached directly.

```java
import io.github.maxymania.powercache.CachingApplier;
import io.github.maxymania.powercache.memcached.MemCachingApplier;

// ...

// create the cache Applier
MemcachedClient c=new MemcachedClient(
    new InetSocketAddress("hostname", portNum));
CachingApplier amp = new MemCachingApplier(c, 60*60*1000);
```

You need the following addins.

```xml
<dependency>
    <groupId>net.spy</groupId>
    <artifactId>spymemcached</artifactId>
    <version>2.12.0</version>
</dependency>
<dependency>
    <groupId>com.esotericsoftware</groupId>
    <artifactId>kryo</artifactId>
    <version>3.0.3</version>
</dependency>
```
