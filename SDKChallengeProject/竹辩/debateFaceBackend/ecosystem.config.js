module.exports = {
  apps : [{
    name: 'ZhuDebate API',
    script: './bin/www',

    args: 'one two',
    instances: 1,
    autorestart: true,
    watch: true,
    ignore_watch: [ 
      'node_modules',
      'logs'
    ],
    error_file: "./logs/app-err.log",
    out_file: "./logs/app-out.log",
    log_date_format: "YYYY-MM-DD HH:mm:ss",
    max_memory_restart: '1G',
    env_dev: {
      "NODE_ENV": "development",
      "REMOTE_ADDR": ""
    }
  }]
};
