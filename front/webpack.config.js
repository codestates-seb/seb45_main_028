const path = require('path');

module.exports = {
  // ... 다른 설정
  resolve: {
    fallback: {
      "crypto": require.resolve("crypto-browserify"),
      "stream": require.resolve("stream-browserify")
    }
  }
};