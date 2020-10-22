#!/usr/bin/env python
import json


def main():
    with open("get.txt", "w") as out:
        with open("../../../latest.txt") as fp:
            for line in fp:
                print(line)
                obj = json.loads(line)
                if "message" in obj:
                    message = obj["message"]
                    if message == "Put record":
                        key = obj["key"].replace("\"", "\\\"")
                        table = obj["table"]
                        version = obj["version"]
                        version_query = f"get '{table}', \"{key}\", {{CONSISTENCY => 'STRONG', TIMESTAMP => {version}}}"
                        print(version_query)
                        out.write(f"{version_query}\n")


if __name__ == "__main__":
    main()
