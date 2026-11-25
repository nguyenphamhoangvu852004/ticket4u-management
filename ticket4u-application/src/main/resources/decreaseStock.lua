local stock = tonumber(redis.call('GET', KEYS[1]));
if (stock >= tonumber(ARGV[1])) then
    redis.call('SET', KEYS[1], stock - tonumber(ARGV[1]));
    return 1;
end;
    return 0;
