# maven-surefire-category-parser-plugin

Use this plugin if you need to combine multiple included and excluded categories in Surefire execution. 

# Usage

Create profiles with their respective include/excluded category combination e.g

```xml

<profile>
  <id>first</id>
  <properties>
    <test.included.category>org.sample.category.Sample</test.included.category>
    <test.excluded.category>org.sample.category.MadHelloWorldCategory</test.excluded.category>
  </properties>
</profile>

<profile>
  <id>second</id>
  <properties>
    <test.included.category>org.sample.category.StarWars</test.included.category>
    <test.excluded.category>org.kitkat.DarthVader</test.excluded.category>
  </properties>
</profile>
```

Add this plugin into the pom's build section e.g

```xml
<build>
  <plugins>
    <plugin>
      <groupId>com.spriadka.mojos</groupId>
      <artifactId>surefire-category-parser-mojo</artifactId>
      <version>1.0-SNAPSHOT</version>
      <executions>
        <execution>
          <goals>
            <goal>parse-categories</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

Executing following command will run Surefire with included and excluded categories from passed in profiles
```bash
mvn -Pfirst,second test
```

where **included** categories are: **[org.sample.category.Sample, org.sample.category.StarWars]**,
and **excluded** categories: **[org.sample.category.MadHelloWorldCategory, org.kitkat.DarthVader]**

# Configuration

By default this plugin would combine and parse categories specified in the **test.included.category** and **test.excluded.category**
properties. To change this behaviour set the desired property value in plugin's configuration e.g

```xml
<profiles>
  <profile>
    <id>first</id>
    <properties>
      <exclude>excluded</exclude>
    </properties>
   </profile>
   <profile>
    <id>second</id>
    <properties>
      <exclude>excluded.excluded</exclude>
    </properties>
   </profile>
</profiles>
<build>
  <plugins>
    <plugin>
      <groupId>com.spriadka.mojos</groupId>
      <artifactId>surefire-category-parser-mojo</artifactId>
      <version>1.0-SNAPSHOT</version>
      <configuration>
        <excludeTestCategory>exclude</excludeTestCategory>
      </configuration>
      <executions>
        <execution>
          <goals>
            <goal>parse-categories</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

