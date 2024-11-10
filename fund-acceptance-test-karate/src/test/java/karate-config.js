function fn() {
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  var config = {
    serviceBaseUrl: karate.properties['karate.serviceBaseUrl'] || 'http://localhost:8080/'
  };

  karate.configure("retry", {count: 5, interval: 5000})
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  karate.configure('ssl', true);
  return config;
}