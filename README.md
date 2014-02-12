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


On Ubuntu I got some unresolved dependencies when I ran npm install in the server directory. I was able to correct these by doing some installs manually and also updating npm and node to their most recent version.
 
Notes on RabbitMQ Install Ubuntu 12.04
For Ubuntu the install of npm installs an older version of node. Need to get the latest version otherwise will get errors:

I  followed the install directions for Rabbit on the following site:
https://www.rabbitmq.com/install-debian.html

I used their APT repository directions:
Our APT repository
To use our APT repository:

Add the following line to your /etc/apt/sources.list:
deb http://www.rabbitmq.com/debian/ testing main
(Please note that the word testing in this line refers to the state of our release of RabbitMQ, not any particular Debian distribution. You can use it with Debian stable, testing or unstable, as well as with Ubuntu. We describe the release as "testing" to emphasise that we release somewhat frequently.)
(optional) To avoid warnings about unsigned packages, add our public key to your trusted key list using apt-key(8):
wget http://www.rabbitmq.com/rabbitmq-signing-key-public.asc
sudo apt-key add rabbitmq-signing-key-public.asc
Run apt-get update.
Install packages as usual; for instance,
sudo apt-get install rabbitmq-server
