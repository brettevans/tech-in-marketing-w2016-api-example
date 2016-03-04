#!/bin/bash -eu

# Provision Vagrant Machine with Clojure

set -o pipefail

if ${trace:-false}; then
  set -x
fi

provision(){
  install-java
  install-curl
  install-boot
}

install-java(){
  echo "Installing Java ..."
  sudo apt-get -y install default-jdk
}

install-curl(){
  echo "Installing Curl ..."
  sudo apt-get -y install curl
}

install-boot(){
  echo "Installing Boot ..."
  curl -LO https://github.com/boot-clj/boot-bin/releases/download/latest/boot.sh > /dev/null
  mv boot.sh boot
  chmod a+x boot
  sudo mv boot /usr/local/bin
}

provision
