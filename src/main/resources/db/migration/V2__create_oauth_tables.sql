-- Таблица 'Токен авторизации'
CREATE TABLE IF NOT  EXISTS oauth_access_token (
  token_id VARCHAR(255),
  token bytea,
  authentication_id VARCHAR(255) PRIMARY KEY,
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication bytea,
  refresh_token VARCHAR(255)
);

-- Таблица 'Токен для перевыпуска токена авторизации'
CREATE TABLE IF NOT  EXISTS  oauth_refresh_token (
  token_id VARCHAR(255),
  token bytea,
  authentication bytea
);
