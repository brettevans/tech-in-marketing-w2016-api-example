# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|
  config.vm.box = "ubuntu/trusty64"

  config.hostmanager.enabled = true
  config.hostmanager.manage_host = true
  config.vm.hostname = "techinmarketing.dev"

  config.vm.synced_folder "./", "/vagrant", type: "rsync", rsync__auto: true, rsync__exclude: "target/"

  config.vm.network "private_network", ip: "192.168.50.4"

  config.vm.network "forwarded_port", guest: 3000, host: 3000
  config.vm.network "forwarded_port", guest: 3001, host: 3001
  config.vm.network "forwarded_port", guest: 3002, host: 3002
  config.vm.network "forwarded_port", guest: 3003, host: 3003

  config.vm.provider "virtualbox" do |vb|
    vb.memory = "4096"
  end

  config.vm.provision "shell", path: "vagrant/base.sh"
  config.vm.provision "shell", path: "vagrant/clojure.sh"
end
