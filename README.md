[![CircleCI](https://circleci.com/gh/moip/mockkid.svg?style=shield)](https://circleci.com/gh/moip/mockkid)

# Mockkid
Mockkid is a webserver for mocking http responses.

## Pre-requisites
- Maven
- Docker (optional)

## Building & running
> mvn clean install

> java -jar target/mockkid.jar

## Running with external configuration files
> java -jar target/mockkid.jar --configuration.path=file:samples/*.yaml

## Running on docker-compose
> docker-compose up --build

Running with docker-compose will point mockkid to the configurations in the *samples* folder.

To use other configurations, edit docker-compose.yml to change the volume or change the samples folder.

## Samples

Complete example:
```json
configuration:
  endpoint:
    url: /myendpoint/fixed
    method: GET
  responseConfigurations:
    - responseConfiguration:
      name: "config1"
      conditional:
        type: EQUALS
        element: headers.authorization
        value: VAVIS
      response:
        status: 200
        headers:
          content-type: application/json
        body: |
          {"xpto": "${headers.authorization}", "status": "WIN"}
    - responseConfiguration:
      name: "config2"
      conditional:
        type: CONTAINS
        element: url.xpto
        value: PETRONI
      response:
        status: 404
        headers:
          content-type: application/json
        body: |
          {"xpto": "${url.xpto}", "status": "FAIL"}
    - responseConfiguration:
      name: "default"
      response:
        status: 401
        headers:
          content-type: application/json
        body: |
          {"xpto": "MY BAD", "status": "FAIL"}
```

More examples can be found at the `samples` directory.

## Contributing

Bug reports and pull requests are welcome on GitHub at https://github.com/moip/mockkid. 
This project is intended to be a safe, welcoming space for collaboration, and contributors
are expected to adhere to the [Contributor Covenant](http://contributor-covenant.org) code of conduct.

1. Fork it (https://github.com/moip/mockkid/fork)
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -am 'Add some feature')
4. Push to the branch (git push origin my-new-feature)
5. Create a new Pull Request

## License

This project is available as open source under the terms of the [MIT License](https://github.com/moip/MockKid/blob/master/LICENSE).
