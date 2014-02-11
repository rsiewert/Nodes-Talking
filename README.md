node-rabbit-prototype
=====================

Notes for installing/setup a nodejs server on Windows 7 (for now just generic)
 install nodejs and npm (nodejs package manager) should be part of the nodejs install
 point your path to the nodejs install dir (want to pickup the node exe and npm script)
 then to test run: npm ls -g
   it should show an empty list since that command just lists your globally installed packages and you don't have any yet
 then from the dir where you cloned the server code run: npm install
   it's running against the file: package.json...which is where I put the dependencies for the project
 now if that works ok then you should be able to run : node server   and it should start the server (check cmd line)...if so goto localhost:3000 and you should see a page with some buttons on it 

 
Notes on RabbitMQ Install Ubuntu 12.04
/////////////////////////////////////

